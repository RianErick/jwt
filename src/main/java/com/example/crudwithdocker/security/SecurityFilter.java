package com.example.crudwithdocker.security;

import com.example.crudwithdocker.model.UserAuth;
import com.example.crudwithdocker.repository.UserAuthRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final UserAuthRepository userAuthRepository;

    public SecurityFilter(TokenService tokenService, UserAuthRepository userAuthRepository) {
        this.tokenService = tokenService;
        this.userAuthRepository = userAuthRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

          System.out.println("Entrou no filtro");
          var tokenJWT = recuperarToken(request);

          if (tokenJWT != null){

              var subject = tokenService.getSubject(tokenJWT);

              UserAuth usuario = userAuthRepository.findByUser(subject).get();

              var auth = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
              SecurityContextHolder.getContext().setAuthentication(auth);
          }

          filterChain.doFilter(request,response);
    }

    private String recuperarToken(HttpServletRequest request) {

        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null){
            return authorizationHeader.replace("Bearer " , "");

        }
           return null;
    }

}
