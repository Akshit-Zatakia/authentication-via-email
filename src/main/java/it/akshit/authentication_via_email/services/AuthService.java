package it.akshit.authentication_via_email.services;

import it.akshit.authentication_via_email.dtos.TokenResponseDto;

public interface AuthService {
    void requestCode(String email);
    TokenResponseDto generateToken(String code);
    TokenResponseDto refreshToken(String refreshToken);
}
