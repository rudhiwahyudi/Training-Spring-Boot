package com.Bootcamp19.RestFullAPI.controller;


import com.Bootcamp19.RestFullAPI.dto.request.CharAnalysisRequest;
import com.Bootcamp19.RestFullAPI.dto.response.BaseResponse;
import com.Bootcamp19.RestFullAPI.dto.response.CharAnalysisResponse;
import com.Bootcamp19.RestFullAPI.service.CharAnalysisService;
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
            @RequestBody CharAnalysisRequest request,
            @RequestHeader(value = "X-Request-Id", required = false) String requestId) {

        log.info("X-Request-Id: {}, kalimat: {}", requestId, request.getKalimat());
        return BaseResponse.ok(charAnalysisService.simpan(request));
    }

    @GetMapping("/{id}")
    public BaseResponse<CharAnalysisResponse> detail(@PathVariable Long id) {
        return charAnalysisService.detail(id);
    }

    @GetMapping("/minimal")
    public BaseResponse<List<CharAnalysisResponse>> minimal(@RequestParam int min) {
        return BaseResponse.ok(charAnalysisService.minimal(min));
    }

    @GetMapping("/terbaru")
    public BaseResponse<List<CharAnalysisResponse>> terbaru() {
        return BaseResponse.ok(charAnalysisService.terbaru());
    }

}
