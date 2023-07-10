package com.library.main.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.main.entity.Users;
import com.library.main.mappers.UserServiceMapper;
import com.library.main.repo.TokenRepository;
import com.library.main.repo.UserRepository;
import com.library.main.security.jwt.JWTService;
import com.library.main.service.AuthService;
import com.library.main.vo.AuthResponse;
import com.library.main.vo.UserRegVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final TokenRepository tokenRepository;

    @Override
    public AuthResponse userAuth(UserRegVO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                request.getPassword()));
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        tokenRepository.save(UserServiceMapper.saveUserToken(user, accessToken));
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var userDetails = repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                var accessToken = jwtService.generateToken(userDetails);
//                revokeAllUserTokens(userDetails);
//                tokenRepository.save(UserServiceMapper.saveUserToken(userDetails, accessToken));
                var authResponse = AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private void revokeAllUserTokens(Users users){
        var validUserTokens=tokenRepository.findAllValidTokenByUser(users.getId());
        if (!validUserTokens.isEmpty()){
            validUserTokens.forEach(token->{
                token.setRevoked(true);
                token.setExpired(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }
    }

}
