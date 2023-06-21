package com.library.main.service;

import com.library.main.vo.AuthResponse;
import com.library.main.vo.UserRegVO;
import com.library.main.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UserService {
    void saveUsers(List<UserVO> userVOList);
    UserVO findUserById(Long userId);

    AuthResponse addUser(UserVO userVO);

    List<UserVO> listAllUsers();
    AuthResponse userAuth(UserRegVO request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
