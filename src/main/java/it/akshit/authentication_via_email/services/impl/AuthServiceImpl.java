package it.akshit.authentication_via_email.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.akshit.authentication_via_email.dtos.EmailDto;
import it.akshit.authentication_via_email.dtos.TokenResponseDto;
import it.akshit.authentication_via_email.exceptions.InvalidDataException;
import it.akshit.authentication_via_email.exceptions.ResourceNotFoundException;
import it.akshit.authentication_via_email.exceptions.UnauthorizedAccessException;
import it.akshit.authentication_via_email.models.LoginCode;
import it.akshit.authentication_via_email.models.User;
import it.akshit.authentication_via_email.repository.LoginCodeRepository;
import it.akshit.authentication_via_email.repository.UserRepository;
import it.akshit.authentication_via_email.services.AuthService;
import it.akshit.authentication_via_email.services.EmailService;
import it.akshit.authentication_via_email.utils.Helper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static it.akshit.authentication_via_email.utils.Constant.ACCESS_TOKEN_EXPIRATION_TIME;
import static it.akshit.authentication_via_email.utils.Constant.REFRESH_TOKEN_EXPIRATION_TIME;


@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final LoginCodeRepository loginCodeRepository;
    private final EmailService emailService;
    @Value("${auth.secretkey}")
    private String SECRET_KEY;

    public AuthServiceImpl(UserRepository userRepository, LoginCodeRepository loginCodeRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.loginCodeRepository = loginCodeRepository;
        this.emailService = emailService;
    }

    @Override
    public void requestCode(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found!");
        }

        String code = Helper.generateOTP(6);
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(15);

        LoginCode loginCode = new LoginCode();
        loginCode.setEmail(email);
        loginCode.setCode(code);
        loginCode.setExpirationTime(expirationTime);

        loginCodeRepository.save(loginCode);
        EmailDto emailDto = EmailDto.builder()
                .to(email)
                .subject("Your Login Code")
                .body("Enter this code: " + code)
                .build();

        emailService.sendEmail(emailDto);
    }

    @Override
    public TokenResponseDto generateToken(String code) {
        Optional<LoginCode> loginCode = loginCodeRepository.findByCode(code);

        if (loginCode.isEmpty() || loginCode.get().getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new InvalidDataException("Invalid or expired code");
        }

        User user = userRepository.findByEmail(loginCode.get().getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate JWT
        String accessToken = Jwts.builder()
                .setClaims(Map.of("roles", user.getRoles(), "sub", loginCode.get().getEmail()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
        String refreshToken = Jwts.builder()
                .setSubject(loginCode.get().getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();

        // Remove the used login token
        loginCodeRepository.delete(loginCode.get());

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public TokenResponseDto refreshToken(String refreshToken) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();

            User user = userRepository.findByEmail(claims.getSubject())
                    .orElseThrow(() -> new RuntimeException("User not found!"));

            String newToken = Jwts.builder()
                    .setSubject(claims.getSubject())
                    .setClaims(Map.of("roles", user.getRoles(), "sub", claims.getSubject()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                    .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .compact();

            return TokenResponseDto.builder()
                    .accessToken(newToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (Exception e) {
            throw new UnauthorizedAccessException("Unauthorized access!");
        }
    }
}
