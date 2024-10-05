package com.springboot.uberprojectauthservice.controllers;

import com.springboot.uberprojectauthservice.dto.AuthRequestDto;
import com.springboot.uberprojectauthservice.dto.PassengerDto;
import com.springboot.uberprojectauthservice.dto.PassengerSignupRequestDto;
import com.springboot.uberprojectauthservice.services.AuthService;
import com.springboot.uberprojectauthservice.services.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Value("${cookie.expiry}")
    private int cookieExpiry;

    private final AuthService authService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService , AuthenticationManager authenticationManager , JwtService jwtService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signUp(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto) {
        PassengerDto response = this.authService.sigunUpPassenger(passengerSignupRequestDto);
        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signInPassenger(@RequestBody AuthRequestDto authRequestDto , HttpServletResponse response) {

        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));
        if(authentication.isAuthenticated()){
            Map<String , Object> map = new HashMap<>();
            map.put("email" , authRequestDto.getEmail());
            String token = this.jwtService.createToken(authRequestDto.getEmail());

            ResponseCookie cookie = ResponseCookie.from("Jwt" , token).maxAge(cookieExpiry)
                    .httpOnly(true)
                    .secure(false)
                    .build();
            response.setHeader(HttpHeaders.SET_COOKIE , cookie.toString());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Authentication failed", HttpStatus.OK);
        }
    }
}
