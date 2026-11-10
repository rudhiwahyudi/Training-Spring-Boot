package com.Bootcamp19.RestFullAPI.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Username wajib diisi") String username,
        @NotBlank(message = "Password wajib diisi") String password) {
}
