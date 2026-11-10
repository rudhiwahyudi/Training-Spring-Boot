package com.Bootcamp19.RestFullAPI.controller;


import com.Bootcamp19.RestFullAPI.dto.request.CharAnalysisRequest;
import com.Bootcamp19.RestFullAPI.dto.response.BaseResponse;
import com.Bootcamp19.RestFullAPI.dto.response.CharAnalysisResponse;
import com.Bootcamp19.RestFullAPI.service.CharAnalysisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AnalyzerController {
    private final CharAnalysisService charAnalysisService;

    @GetMapping
    public BaseResponse<List<CharAnalysisResponse>> cari(@RequestParam(required = false) String kalimat) {
        return BaseResponse.ok(charAnalysisService.cari(kalimat));
    }

    @PostMapping
    public BaseResponse<CharAnalysisResponse> simpan(
            @Valid
            @RequestBody CharAnalysisRequest request,
            @RequestHeader(value = "X-Request-Id", required = false) String requestId) {

        log.info("X-Request-Id: {}, kalimat: {}", requestId, request.getKalimat());
        return BaseResponse.ok(charAnalysisService.simpan(request));
    }

    @GetMapping("/{id:\\d+}")
    public BaseResponse<CharAnalysisResponse> detail(@PathVariable Long id)
    {
        return BaseResponse.ok(charAnalysisService.detail(id));
    }

    @GetMapping("/minimal")
    public BaseResponse<List<CharAnalysisResponse>> minimal(@RequestParam int min) {
        return BaseResponse.ok(charAnalysisService.minimal(min));
    }

    @GetMapping("/terbaru")
    public BaseResponse<List<CharAnalysisResponse>> terbaru() {
        return BaseResponse.ok(charAnalysisService.terbaru());
    }

    @PostMapping("/from-quote")
    public BaseResponse<CharAnalysisResponse> analisaQuote() {
        return BaseResponse.ok(charAnalysisService.analisaDataQuote());
    }

    @PostMapping("/from-quote/{id}")
    public BaseResponse<CharAnalysisResponse> analisaQuoteById(@PathVariable Long id) {
        return BaseResponse.ok(charAnalysisService.analisaQuoteById(id));
    }

    @PostMapping("/from-quote/batch/{jumlah}")
    public BaseResponse<List<CharAnalysisResponse>> analisaQuoteBatch(@PathVariable int jumlah) {
        return BaseResponse.ok(charAnalysisService.analisaQuoteBatch(jumlah));
    }

    @PostMapping("/from-quote/page")
    public BaseResponse<List<CharAnalysisResponse>> analisaQuotePage(
            @RequestParam int limit,
            @RequestParam int skip) {
        return BaseResponse.ok(charAnalysisService.analisaQuotePage(limit, skip));
    }

}
