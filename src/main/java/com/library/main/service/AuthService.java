package com.library.main.service;

import com.library.main.vo.AuthResponse;
import com.library.main.vo.UserRegVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthService {
    AuthResponse userAuth(UserRegVO request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
