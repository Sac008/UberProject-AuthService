package com.springboot.uberprojectauthservice.services;

import com.springboot.uberprojectauthservice.helpers.AuthPassengerDetail;
import com.springboot.uberprojectauthservice.models.Passenger;
import com.springboot.uberprojectauthservice.repositories.PassengerRepository;
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

    private PassengerRepository passengerRepository;

    public UserDetailsServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Passenger> Passenger = passengerRepository.findByEmail(username);
        if(Passenger.isPresent()) {
            return new AuthPassengerDetail(Passenger.get());
        }
        else {
            throw new UsernameNotFoundException("Cannot find passenger by given username/email");
        }
    }
}
