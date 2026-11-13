package com.Bootcamp19.RestFullAPI.service;

import com.Bootcamp19.RestFullAPI.dto.request.AuthRequestDto;
import com.Bootcamp19.RestFullAPI.dto.response.AuthResponseDto;

public interface AuthServiceJWT {

    String register(AuthRequestDto authRequestDto);
    AuthResponseDto login(AuthRequestDto authRequestDto);

}
