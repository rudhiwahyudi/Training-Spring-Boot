package com.Bootcamp19.RestFullAPI.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequestDto {

    @NotBlank(message = "Usrenam wajib diisi")
    private String username;

    @NotBlank(message = "Password wajib diiis")
    @Size(min = 8, message = "Password minimal 8 character")
    private String password;

}
