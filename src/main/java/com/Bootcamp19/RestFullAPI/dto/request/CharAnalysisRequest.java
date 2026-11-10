package com.Bootcamp19.RestFullAPI.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class CharAnalysisRequest {

    @NotBlank(message = "Kalimat wajib di isi")
    @Size(min = 5, max = 500, message = "Kalimat maximal 500 karakter")
    private String kalimat;
}
