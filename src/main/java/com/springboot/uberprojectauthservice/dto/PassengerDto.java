package com.springboot.uberprojectauthservice.dto;

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

}
