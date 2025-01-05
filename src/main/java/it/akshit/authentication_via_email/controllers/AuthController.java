package it.akshit.authentication_via_email.controllers;

import it.akshit.authentication_via_email.dtos.TokenResponseDto;
import it.akshit.authentication_via_email.dtos.UserDto;
import it.akshit.authentication_via_email.services.AuthService;
import it.akshit.authentication_via_email.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto){
        UserDto user = userService.createUser(userDto);
        authService.requestCode(user.getEmail());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody Map<String, String> body) {
        authService.requestCode(body.get("email"));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenResponseDto> authenticate(@RequestBody Map<String, String> body) {
        TokenResponseDto response = authService.generateToken(body.get("code"));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> refresh(@RequestBody Map<String, String> body) {
        String authHeader = body.get("refreshToken");
        TokenResponseDto response = authService.refreshToken(authHeader);
        return ResponseEntity.ok(response);
    }
}
