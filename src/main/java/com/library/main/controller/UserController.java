package com.library.main.controller;

import com.library.main.service.UserService;
import com.library.main.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/")
public class UserController {

    private final UserService service;

    @PostMapping("save-users")
    public ResponseEntity<String> saveUsers(@RequestBody List<UserVO> userVOList) {
        service.saveUsers(userVOList);
        return ResponseEntity.ok().body("USERS_SAVED");
    }

    @PostMapping("get-user")
    public ResponseEntity<UserVO> getUserById(@RequestParam Long userId) {
        return ResponseEntity.ok().body(service.findUserById(userId));
    }

    @PostMapping("add-user")
    public ResponseEntity<String> addUser(@RequestBody UserVO userVO){
        service.addUser(userVO);
        return ResponseEntity.accepted().body("USER_SAVED");
    }

    @PostMapping("get-all-users")
    public ResponseEntity<List<UserVO>> listAllUsers(){
        return ResponseEntity.ok().body(service.listAllUsers());
    }
}
