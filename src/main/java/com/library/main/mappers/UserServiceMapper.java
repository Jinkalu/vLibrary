package com.library.main.mappers;

import com.library.main.entity.Token;
import com.library.main.entity.TokenType;
import com.library.main.entity.Users;

/**
 * mapper class
 */
public class UserServiceMapper {

    private UserServiceMapper() {
    }

    public static Token saveUserToken(Users user, String accessToken) {
        return Token.builder()
                .user(user)
                .tokenType(TokenType.BEARER)
                .accessToken(accessToken)
                .revoked(false)
                .expired(false)
                .build();
    }
}
