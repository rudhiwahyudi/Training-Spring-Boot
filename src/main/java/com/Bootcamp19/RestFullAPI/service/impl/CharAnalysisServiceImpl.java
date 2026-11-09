package com.Bootcamp19.RestFullAPI.service.impl;


import com.Bootcamp19.RestFullAPI.dto.request.CharAnalysisRequest;
import com.Bootcamp19.RestFullAPI.dto.response.BaseResponse;
import com.Bootcamp19.RestFullAPI.dto.response.CharAnalysisResponse;
import com.Bootcamp19.RestFullAPI.entity.CharAnalysis;
import com.Bootcamp19.RestFullAPI.repository.CharAnalysisRepository;
import com.Bootcamp19.RestFullAPI.service.CharAnalysisService;
import com.Bootcamp19.RestFullAPI.util.CharCounter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CharAnalysisServiceImpl implements CharAnalysisService {

    private final CharAnalysisRepository charAnalysisRepository;


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
        CharCounter counter = new CharCounter(request.getKalimat());

        CharAnalysis entity = new CharAnalysis();
        entity.setKalimat(request.getKalimat());
        entity.setTotalHuruf(counter.getTotalHuruf());
        entity.setCreatedAt(LocalDateTime.now());

        CharAnalysis saved = charAnalysisRepository.save(entity);
        return toResponse(saved);
    }

    private CharAnalysisResponse toResponse(CharAnalysis date) {
        CharCounter counter = new CharCounter(date.getKalimat());
        return CharAnalysisResponse.from(date, counter.toMap());
    }

    @Override
    public BaseResponse<CharAnalysisResponse> detail(Long id) {
        Optional<CharAnalysis> data = charAnalysisRepository.findById(id);
        if(data.isEmpty()) {
            return BaseResponse.error("Data dengan id " + id + " tidak ditemukan");
        }
        return BaseResponse.ok(toResponse(data.get()));
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


}
