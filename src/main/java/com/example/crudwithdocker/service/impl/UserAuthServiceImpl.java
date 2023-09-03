package com.example.crudwithdocker.service.impl;

import com.example.crudwithdocker.model.UserAuth;
import com.example.crudwithdocker.repository.UserAuthRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthServiceImpl implements UserDetailsService {

    private  final PasswordEncoder passwordEncoder;

    private final UserAuthRepository userAuthRepository;

    public UserAuthServiceImpl(PasswordEncoder passwordEncoder, UserAuthRepository userAuthRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          return userAuthRepository.findByUser(username).get();


    }

    public UserAuth salvarUsuario(UserAuth userAuth){

        boolean isUserPresent = userAuthRepository.findByUser(userAuth.getUser()).isPresent();
        if(isUserPresent){
            throw new RuntimeException("Usuario Existente");
        }
        userAuth.setPassword(passwordEncoder.encode(userAuth.getPassword()));
        return userAuthRepository.save(userAuth);


    }

}