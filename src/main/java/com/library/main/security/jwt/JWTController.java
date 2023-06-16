package com.library.main.security.jwt;

import com.library.main.service.UserService;
import com.library.main.vo.AuthResponse;
import com.library.main.vo.UserRegVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth/")
public class JWTController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<AuthResponse> userAuth(@RequestBody UserRegVO regVO){
        return ResponseEntity.ok().body(service.userAuth(regVO));
    }
}
