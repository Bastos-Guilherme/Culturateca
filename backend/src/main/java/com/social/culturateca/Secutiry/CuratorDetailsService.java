package com.social.culturateca.Secutiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.social.culturateca.model.Curator;
import com.social.culturateca.model.repository.CuratorRepository;

@Service
public class CuratorDetailsService implements UserDetailsService {

    @Autowired 
    CuratorRepository curatorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) 
        throws UsernameNotFoundException {
        
        Curator user = curatorRepository.findById(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User
            .withUsername(user.getEmail())
            .password(user.getPassword())
            .roles("USER")
            .build();
    }
}