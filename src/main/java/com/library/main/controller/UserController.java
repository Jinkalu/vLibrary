package com.library.main.controller;

import com.library.main.vo.UserVO;
import lombok.RequiredArgsConstructor;
import com.library.main.vo.AuthResponse;
import com.library.main.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService service;

    @PostMapping("save-users")
    @PreAuthorize("hasAnyAuthority('admin:create')")
    public ResponseEntity<String> saveUsers(@RequestBody List<UserVO> userVOList) {
        service.saveUsers(userVOList);
        return ResponseEntity.ok().body("USERS_SAVED");
    }

    @PostMapping("add-user")
    @PreAuthorize("hasAnyAuthority('admin:create')")
    public ResponseEntity<AuthResponse> addUser(@RequestBody UserVO userVO){
        return ResponseEntity.accepted().body(service.addUser(userVO));
    }

    @GetMapping("get-user")
    @PreAuthorize("hasAnyAuthority('admin:read')")
    public ResponseEntity<UserVO> getUserById(@RequestParam Long userId) {
        return ResponseEntity.ok().body(service.findUserById(userId));
    }

    @GetMapping("get-all-users")
    @PreAuthorize("hasAnyAuthority('admin:read')")
    public ResponseEntity<List<UserVO>> listAllUsers(){
        return ResponseEntity.ok().body(service.listAllUsers());
    }
}
