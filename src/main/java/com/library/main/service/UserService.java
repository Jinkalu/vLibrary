package com.library.main.service;

import com.library.main.vo.UserVO;

import java.util.List;

public interface UserService {
    void saveUsers(List<UserVO> userVOList);

    UserVO findUserById(Long userId);

    void addUser(UserVO userVO);

    List<UserVO> listAllUsers();
}
