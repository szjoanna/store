package io.asia.store.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.asia.store.domain.dto.LoginDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl("/api/login");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Claims claims = new DefaultClaims()
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .setSubject(((UserDetails) authResult.getPrincipal()).getUsername());
        claims.put("authorities", authResult.getAuthorities().stream()
                .map(GrantedAuthority :: getAuthority)
                .collect(Collectors.joining(",")));
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "store")
                .compact();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(response.getWriter(), Collections.singletonMap("token", token));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if(!request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("login method not supported " + request.getMethod());
        }
        try {
            LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
            return getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
    }
}
