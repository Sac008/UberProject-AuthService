package com.springboot.uberprojectauthservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerSignupRequestDto {

    private String name;

    private String email;

    private String password;

    private String phoneNumber;
}
