package com.example.crudwithdocker.controller;

import com.example.crudwithdocker.model.UserAuth;
import com.example.crudwithdocker.model.record.UserAuthRecord;
import com.example.crudwithdocker.repository.UserAuthRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {



    private  final PasswordEncoder passwordEncoder;

    private final UserAuthRepository userAuthRepository;

    private final AuthenticationManager manager;

    public AuthController(PasswordEncoder passwordEncoder,
                          UserAuthRepository userAuthRepository,
                          AuthenticationManager manager) {
        this.passwordEncoder = passwordEncoder;
        this.userAuthRepository = userAuthRepository;
        this.manager = manager;
    }

    @PostMapping("/login")
    public ResponseEntity authLogin(@RequestBody UserAuthRecord userAuthRecord){

         var login = new UsernamePasswordAuthenticationToken(userAuthRecord.user()
                                                             ,userAuthRecord.password());
         manager.authenticate(login);
         return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public UserAuth userAuth(@RequestBody UserAuth userAuth){
        userAuth.setPassword(passwordEncoder.encode(userAuth.getPassword()));
        return userAuthRepository.save(userAuth);

    }
}
