package com.example.crudwithdocker.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import com.example.crudwithdocker.model.UserAuth;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    public String gerarToken(UserAuth userAuth){

        try {
            Algorithm algorithm = Algorithm.HMAC256("123");
          return JWT.create()
                    .withIssuer("auth0")
                    .withSubject(userAuth.getUser())
                    .withIssuedAt(LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.of("-03:00")))
                    .sign(algorithm);
        } catch (JWTCreationException exception){

            throw new RuntimeException(exception);
        }

    }

}

