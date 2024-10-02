package com.springboot.uberprojectauthservice.dto;

import com.springboot.uberprojectauthservice.models.Passenger;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto {

    private String id;

    private String name;

    private String email;

    private String password; // Encrypted password

    private String phoneNumber;

    private Date createdAt;

    public static PassengerDto fromPassenger(Passenger passenger) {
        return PassengerDto.builder()
                .id(passenger.getId().toString())
                .email(passenger.getEmail())
                .createdAt(passenger.getCreatedAt())
                .name(passenger.getName())
                .password(passenger.getPassword())
                .phoneNumber(passenger.getPhoneNumber())
                .build();
    }

}
