package com.greenblat.redditclone.controller;

import com.greenblat.redditclone.dto.AuthenticationResponse;
import com.greenblat.redditclone.dto.LoginRequest;
import com.greenblat.redditclone.dto.RefreshTokenRequest;
import com.greenblat.redditclone.dto.RegisterRequest;
import com.greenblat.redditclone.service.AuthService;
import com.greenblat.redditclone.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        AuthenticationResponse authenticationResponse = authService.signup(registerRequest);
        return new ResponseEntity<>("User register successful. \n Response: " + authenticationResponse, HttpStatus.OK);
    }

    @GetMapping("/account_verification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity
                .status(OK)
                .body("Refresh Token Deleted Successfully!!");
    }

}
