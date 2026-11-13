package com.Bootcamp19.RestFullAPI.service.impl;


import com.Bootcamp19.RestFullAPI.dto.feign.QuoteResponse;
import com.Bootcamp19.RestFullAPI.rest.QuoteClient;
import com.Bootcamp19.RestFullAPI.service.QuoteAsyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuoteAsyncServiceImpl implements QuoteAsyncService {

    private final QuoteClient quoteClient;

    @Async
    @Override
    public CompletableFuture<QuoteResponse> getQuote() {
        log.info("[{}] mulai catat riwayat id={}", Thread.currentThread().getName());
        QuoteResponse quoteResponse = quoteClient.getQuote();
        log.info("[{}] mulai catat riwayat id={}", Thread.currentThread().getName(), quoteResponse.getId());
        return CompletableFuture.completedFuture(quoteResponse);
    }
}
