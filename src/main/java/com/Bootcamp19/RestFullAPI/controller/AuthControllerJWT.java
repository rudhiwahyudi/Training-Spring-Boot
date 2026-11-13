package com.Bootcamp19.RestFullAPI.controller;


import com.Bootcamp19.RestFullAPI.dto.request.AuthRequestDto;
import com.Bootcamp19.RestFullAPI.dto.response.AuthResponseDto;
import com.Bootcamp19.RestFullAPI.dto.response.BaseResponse;
import com.Bootcamp19.RestFullAPI.service.AuthService;
import com.Bootcamp19.RestFullAPI.service.AuthServiceJWT;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class AuthControllerJWT {

    private final AuthServiceJWT authServiceJWT;

    @PostMapping("/register")
    public BaseResponse<String> register(
            @Valid
            @RequestBody
            AuthRequestDto authRequestDto){
        return BaseResponse.ok(authServiceJWT.register(authRequestDto));
    }

    @PostMapping("/login")
    public BaseResponse<AuthResponseDto> login(
            @Valid
            @RequestBody
            AuthRequestDto authRequestDto) {
        return BaseResponse.ok(authServiceJWT.login(authRequestDto));

    }
}
