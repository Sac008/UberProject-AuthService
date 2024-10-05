package com.springboot.uberprojectauthservice.controllers;

import com.springboot.uberprojectauthservice.dto.PassengerDto;
import com.springboot.uberprojectauthservice.dto.PassengerSignupRequestDto;
import com.springboot.uberprojectauthservice.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signUp(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto) {
        PassengerDto response = this.authService.sigunUpPassenger(passengerSignupRequestDto);
        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }

    @GetMapping("/signin/passenger")
    public ResponseEntity<?> signInPassenger(String token) {return null;}
}
