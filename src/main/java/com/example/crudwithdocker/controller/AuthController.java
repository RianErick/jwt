package com.example.crudwithdocker.controller;

import com.example.crudwithdocker.model.UserAuth;
import com.example.crudwithdocker.model.record.UserAuthRecord;
import com.example.crudwithdocker.repository.UserAuthRepository;
import com.example.crudwithdocker.security.DadosToken;
import com.example.crudwithdocker.security.TokenService;
import com.example.crudwithdocker.service.impl.UserAuthServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserAuthServiceImpl userAuthService;
    private final UserAuthRepository userAuthRepository;

    private final AuthenticationManager manager;

    private final TokenService tokenService;

    public AuthController(PasswordEncoder passwordEncoder,
                          UserAuthServiceImpl userAuthService, UserAuthRepository userAuthRepository,
                          AuthenticationManager manager, TokenService tokenService) {
        this.userAuthService = userAuthService;

        this.userAuthRepository = userAuthRepository;
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity authLogin(@RequestBody UserAuthRecord userAuthRecord){

         var login = new UsernamePasswordAuthenticationToken(userAuthRecord.user(),userAuthRecord.password());
         var authentication = manager.authenticate(login);
         var tokenJWT  = tokenService.gerarToken((UserAuth) authentication.getPrincipal());

        System.out.println(tokenJWT);

         return ResponseEntity.ok(new DadosToken(tokenJWT));

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public UserAuth userAuth(@RequestBody UserAuth userAuth){

        return userAuthService.salvarUsuario(userAuth);

    }
}
