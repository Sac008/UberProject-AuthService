package com.springboot.uberprojectauthservice.services;

import com.springboot.uberprojectauthservice.dto.PassengerDto;
import com.springboot.uberprojectauthservice.dto.PassengerSignupRequestDto;
import com.springboot.uberprojectauthservice.models.Passenger;
import com.springboot.uberprojectauthservice.repositories.PassengerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final PassengerRepository passengerRepository;

    public AuthService(PassengerRepository passengerRepository , BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passengerRepository = passengerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public PassengerDto sigunUpPassenger(PassengerSignupRequestDto passengerSignupRequestDto) {
        Passenger passenger = Passenger.builder()
                .email(passengerSignupRequestDto.getEmail())
                .name(passengerSignupRequestDto.getName())
                .phoneNumber(passengerSignupRequestDto.getPhoneNumber())
                .password(bCryptPasswordEncoder.encode(passengerSignupRequestDto.getPassword()))
                .build();

        return PassengerDto.fromPassenger(passengerRepository.save(passenger));
    }
}
