package com.Bootcamp19.RestFullAPI.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {

    private String username;

    private String token;

}
