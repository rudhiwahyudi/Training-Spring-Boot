package com.Bootcamp19.RestFullAPI.rest;


import com.Bootcamp19.RestFullAPI.dto.feign.QuotePageResponse;
import com.Bootcamp19.RestFullAPI.dto.feign.QuoteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "quoteClient", url = "${quote.api.url}")
public interface QuoteClient {

    @GetMapping("/quotes/random")
    QuoteResponse getQuote();

    @GetMapping("/quotes/{id}")
    QuoteResponse getQuoteById(@PathVariable("id") Long id);

    @GetMapping("/quotes/random/{length}")
    List<QuoteResponse> getRandomQuotes(@PathVariable("length") int length);

    @GetMapping("/quotes")
    QuotePageResponse getQuotes(@RequestParam("limit") int limit, @RequestParam("skip") int skip);
}
