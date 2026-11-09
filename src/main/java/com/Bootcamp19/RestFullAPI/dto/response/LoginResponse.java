package com.Bootcamp19.RestFullAPI.dto.response;

public record LoginResponse(boolean success, String message, String username) {
}
