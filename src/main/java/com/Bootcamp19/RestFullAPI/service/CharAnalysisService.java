package com.Bootcamp19.RestFullAPI.service;

import com.Bootcamp19.RestFullAPI.dto.request.CharAnalysisRequest;
import com.Bootcamp19.RestFullAPI.dto.response.CharAnalysisResponse;

import java.util.List;

public interface CharAnalysisService {
    List<CharAnalysisResponse> cari(String kata);
    CharAnalysisResponse simpan (CharAnalysisRequest request);
    CharAnalysisResponse detail(Long id);
    List<CharAnalysisResponse> minimal(int min);
    List<CharAnalysisResponse> terbaru();
    CharAnalysisResponse analisaDataQuote();
    CharAnalysisResponse analisaQuoteById(Long id);
    List<CharAnalysisResponse> analisaQuoteBatch(int jumlah);
    List<CharAnalysisResponse> analisaQuotePage(int limit, int skip);
    List<CharAnalysisResponse> analysisDoubleQuote();
}
