package com.Bootcamp19.RestFullAPI.service;

import com.Bootcamp19.RestFullAPI.dto.feign.QuoteResponse;

import java.util.concurrent.CompletableFuture;

public interface QuoteAsyncService {
    CompletableFuture<QuoteResponse> getQuote();

}
