package com.example.crudwithdocker.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.crudwithdocker.model.UserAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token}")
    private String secret;

    public String gerarToken(UserAuth userAuth){

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
          return JWT.create()
                    .withIssuer("exemploJwt")
                    .withSubject(userAuth.getUser())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(dataInspiracaoToken())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException(exception);
        }

    }

    public String getSubject(String tokenJWT){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
           return JWT.require(algorithm)
                    .withIssuer("exemploJwt")
                    .build()
                    .verify(tokenJWT).getSubject();

        } catch (JWTVerificationException exception){
            throw  new RuntimeException("Token Invalido Ou expirado! Ou " + exception);
        }

    }

    public Instant dataInspiracaoToken(){
       return LocalDateTime.now()
               .plusSeconds(40)
               .toInstant(ZoneOffset.of("-03:00"));
}

}

