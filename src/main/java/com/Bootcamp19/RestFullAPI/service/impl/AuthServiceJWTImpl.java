package com.Bootcamp19.RestFullAPI.service.impl;

import com.Bootcamp19.RestFullAPI.Exception.UnauthorizedException;
import com.Bootcamp19.RestFullAPI.Exception.UserAlreadyExistException;
import com.Bootcamp19.RestFullAPI.dto.request.AuthRequestDto;
import com.Bootcamp19.RestFullAPI.dto.response.AuthResponseDto;
import com.Bootcamp19.RestFullAPI.entity.UserEntity;
import com.Bootcamp19.RestFullAPI.repository.UserRepository;
import com.Bootcamp19.RestFullAPI.service.AuthServiceJWT;
import com.Bootcamp19.RestFullAPI.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceJWTImpl implements AuthServiceJWT {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;


    @Override
    public String register(AuthRequestDto authRequestDto) {
        if(userRepository.existsByUsername(authRequestDto.getUsername())){
            throw new UserAlreadyExistException(authRequestDto.getUsername());
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(authRequestDto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(authRequestDto.getPassword()));
        userRepository.save(userEntity);
        return "User " +userEntity.getUsername()+ " has been registterd seuccess";
    }

    @Override
    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        UserEntity userEntity = userRepository.findByUsername((authRequestDto.getUsername()))
                .orElseThrow(() -> new UnauthorizedException("User password salah"));

        if(!passwordEncoder.matches(authRequestDto.getPassword(), userEntity.getPassword())){
            throw new UnauthorizedException("Password atau username salah");
        }

        String token = jwtUtil.generateToken((userEntity.getUsername()));
        return new AuthResponseDto(userEntity.getUsername(), token);
    }
}
