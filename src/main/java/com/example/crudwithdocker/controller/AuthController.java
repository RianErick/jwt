package com.example.crudwithdocker.controller;

import com.example.crudwithdocker.model.UserAuth;
import com.example.crudwithdocker.model.record.UserAuthRecord;
import com.example.crudwithdocker.repository.UserAuthRepository;
import com.example.crudwithdocker.security.TokenService;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {



    private  final PasswordEncoder passwordEncoder;

    private final UserAuthRepository userAuthRepository;

    private final AuthenticationManager manager;

    private final TokenService tokenService;

    public AuthController(PasswordEncoder passwordEncoder,
                          UserAuthRepository userAuthRepository,
                          AuthenticationManager manager, TokenService tokenService) {
        this.passwordEncoder = passwordEncoder;
        this.userAuthRepository = userAuthRepository;
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity authLogin(@RequestBody UserAuthRecord userAuthRecord){

         var login = new UsernamePasswordAuthenticationToken(userAuthRecord.user()
                                                             ,userAuthRecord.password());
         var authentication = manager.authenticate(login);
         return ResponseEntity.ok(tokenService.gerarToken((UserAuth) authentication.getPrincipal()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public UserAuth userAuth(@RequestBody UserAuth userAuth){
        userAuth.setPassword(passwordEncoder.encode(userAuth.getPassword()));
        return userAuthRepository.save(userAuth);

    }
}
