package com.Bootcamp19.RestFullAPI.service.impl;


import com.Bootcamp19.RestFullAPI.Exception.AnalysisAlreadyExistsException;
import com.Bootcamp19.RestFullAPI.Exception.AnalysisNotFoundException;
import com.Bootcamp19.RestFullAPI.Exception.QuoteNotFoundException;
import com.Bootcamp19.RestFullAPI.Exception.QuoteUnavailableException;
import com.Bootcamp19.RestFullAPI.dto.feign.QuotePageResponse;
import com.Bootcamp19.RestFullAPI.dto.feign.QuoteResponse;
import com.Bootcamp19.RestFullAPI.dto.request.CharAnalysisRequest;
import com.Bootcamp19.RestFullAPI.dto.response.CharAnalysisResponse;
import com.Bootcamp19.RestFullAPI.entity.CharAnalysis;
import com.Bootcamp19.RestFullAPI.repository.CharAnalysisRepository;
import com.Bootcamp19.RestFullAPI.rest.QuoteClient;
import com.Bootcamp19.RestFullAPI.service.CharAnalysisService;
import com.Bootcamp19.RestFullAPI.service.QuoteAsyncService;
import com.Bootcamp19.RestFullAPI.service.RiwayatService;
import com.Bootcamp19.RestFullAPI.util.CharCounter;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CharAnalysisServiceImpl implements CharAnalysisService {

    private static final int MAX_KALIMAT_LENGTH = 500;
    private static final int MAX_BATCH = 10;
    private static final int MAX_PAGE_LIMIT = 50;

    private final CharAnalysisRepository charAnalysisRepository;
    private final QuoteClient client;
    private final RiwayatService riwayatService;
    private final QuoteAsyncService quoteAsyncService;


    @Override
    public List<CharAnalysisResponse> cari(String kata) {
        List<CharAnalysis> semua;
        if (kata == null || kata.isBlank()){
            semua = charAnalysisRepository.findAll();
        }
        else {
            semua = charAnalysisRepository.findByKalimatContainingIgnoreCase(kata);
        }

        List<CharAnalysisResponse> hasil = new ArrayList<>();
        for (CharAnalysis data:semua) {
            hasil.add(toResponse(data));
        }
        return hasil;
    }

    @Override
    public CharAnalysisResponse simpan(CharAnalysisRequest request) {
        String kalimat = request.getKalimat() == null ? null : request.getKalimat().trim();

        if (kalimat == null || kalimat.isBlank()) {
            throw new IllegalArgumentException("Kalimat tidak boleh kosong");
        }

        if (charAnalysisRepository.existsByKalimatIgnoreCase(kalimat)) {
            throw new AnalysisAlreadyExistsException(kalimat);
        }

        return simpan(kalimat, null);
    }

    private CharAnalysisResponse simpan(String kalimat, String author) {
        CharCounter counter = new CharCounter(kalimat);

        CharAnalysis entity = new CharAnalysis();
        entity.setKalimat(kalimat);
        entity.setAuthor(author);
        entity.setTotalHuruf(counter.getTotalHuruf());
        entity.setCreatedAt(LocalDateTime.now());

        CharAnalysis saved = charAnalysisRepository.save(entity);

        riwayatService.catatanRiwayat(entity.getId(), kalimat);
        log.info("[{}] mulai catat riwayat id={}", Thread.currentThread().getName());

        return toResponse(saved);
    }

    private CharAnalysisResponse toResponse(CharAnalysis date) {
        CharCounter counter = new CharCounter(date.getKalimat());
        return CharAnalysisResponse.from(date, counter.toMap());
    }

    @Override
    public CharAnalysisResponse detail(Long id) {
        CharAnalysis data = charAnalysisRepository.findById(id)
                .orElseThrow(() -> new AnalysisNotFoundException(id));
        return toResponse(data);
    }

    @Override
    public List<CharAnalysisResponse> minimal(int min) {
        return charAnalysisRepository.findMinimal(min).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<CharAnalysisResponse> terbaru() {
        return charAnalysisRepository.findTerbaru().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CharAnalysisResponse analisaDataQuote() {
        QuoteResponse quote;
        try {
            quote = client.getQuote();
        } catch (FeignException feignException) {
            throw new QuoteUnavailableException("Gagal mengambil quote dari layanan eksternal");
        }

        return simpanClient(quote);
    }

    @Override
    public CharAnalysisResponse analisaQuoteById(Long id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("Id quote minimal 1");
        }

        QuoteResponse quote;
        try {
            quote = client.getQuoteById(id);
        } catch (FeignException.NotFound notFoundException) {
            throw new QuoteNotFoundException(id);
        } catch (FeignException feignException) {
            throw new QuoteUnavailableException("Gagal mengambil quote dari layanan eksternal");
        }

        return simpanClient(quote);
    }

    @Override
    public List<CharAnalysisResponse> analisaQuoteBatch(int jumlah) {
        if (jumlah < 1 || jumlah > MAX_BATCH) {
            throw new IllegalArgumentException("Jumlah quote harus antara 1 sampai " + MAX_BATCH);
        }

        List<QuoteResponse> quotes;
        try {
            quotes = client.getRandomQuotes(jumlah);
        } catch (FeignException feignException) {
            throw new QuoteUnavailableException("Gagal mengambil quote dari layanan eksternal");
        }

        return simpanSemua(quotes);
    }

    @Override
    public List<CharAnalysisResponse> analisaQuotePage(int limit, int skip) {
        if (limit < 1 || limit > MAX_PAGE_LIMIT) {
            throw new IllegalArgumentException("Limit harus antara 1 sampai " + MAX_PAGE_LIMIT);
        }
        if (skip < 0) {
            throw new IllegalArgumentException("Skip tidak boleh negatif");
        }

        QuotePageResponse page;
        try {
            page = client.getQuotes(limit, skip);
        } catch (FeignException feignException) {
            throw new QuoteUnavailableException("Gagal mengambil quote dari layanan eksternal");
        }

        if (page == null || page.getQuotes() == null) {
            throw new QuoteUnavailableException("Daftar quote dari layanan eksternal kosong");
        }

        return simpanSemua(page.getQuotes());
    }

    @Override
    public List<CharAnalysisResponse> analysisDoubleQuote() {
        Long mulai = System.currentTimeMillis();
        CompletableFuture<QuoteResponse> quote1 = quoteAsyncService.getQuote();
        CompletableFuture<QuoteResponse> quote2 = quoteAsyncService.getQuote();

        List<CharAnalysisResponse> hasil = new ArrayList<>();
        try{
            QuoteResponse hasilquote1 = quote1.join();
            QuoteResponse hasilquote2 = quote2.join();
            hasil.add(simpan(hasilquote1.getQuote(), hasilquote1.getAuthor()));
            hasil.add(simpan(hasilquote2.getQuote(), hasilquote2.getAuthor()));
        } catch (CompletionException completionException) {
            throw new QuoteUnavailableException("Service Unavailable");
        }
        log.info("[{}] dua quote selesai dalam {} ms", Thread.currentThread().getName(), System.currentTimeMillis() -mulai);
        return hasil;
    }

    private List<CharAnalysisResponse> simpanSemua(List<QuoteResponse> quotes) {
        List<CharAnalysisResponse> hasil = new ArrayList<>();
        for (QuoteResponse quote : quotes) {
            hasil.add(simpanClient(quote));
        }
        return hasil;
    }

    private CharAnalysisResponse simpanClient(QuoteResponse quote) {
        if (quote == null || quote.getQuote() == null || quote.getQuote().isBlank()) {
            throw new QuoteUnavailableException("Quote yang diterima dari layanan eksternal kosong");
        }

        String kalimat = quote.getQuote().trim();
        if (kalimat.length() > MAX_KALIMAT_LENGTH) {
            throw new QuoteUnavailableException("Quote dari layanan eksternal melebihi " + MAX_KALIMAT_LENGTH + " karakter");
        }

        return simpan(kalimat, quote.getAuthor());
    }


}
