package com.library.main.service;

import com.library.main.vo.AuthResponse;
import com.library.main.vo.UserVO;

import java.util.List;

public interface UserService {
    void saveUsers(List<UserVO> userVOList);
    UserVO findUserById(Long userId);
    AuthResponse addUser(UserVO userVO);
    List<UserVO> listAllUsers();
}
