package com.studyretro.config;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.studyretro.entity.Users;
import com.studyretro.repository.UserRepository;
import com.studyretro.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            try {
                String email = jwtService.getEmail(token);
                Optional<Users> user = userRepository.findByEmail(email);
                if(user.isPresent()) {
                    Users newUser = user.get();
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (JWTDecodeException ignored){}
        }
        filterChain.doFilter(request, response);
    }
}