package com.springboot.uberprojectauthservice.services;

import com.springboot.uberprojectauthservice.helpers.AuthPassengerDetail;
import com.springboot.uberprojectauthservice.models.Passenger;
import com.springboot.uberprojectauthservice.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
    This class is responsible for loading the user in the form of UserDetails object for Auth.
*/


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PassengerRepository passengerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<Passenger> passenger = this.passengerRepository.findPassengerByEmail(username);
            if(passenger.isPresent()) {
                return new AuthPassengerDetail(passenger.get());
            }
            throw new UsernameNotFoundException("Passenger not found");
    }
}