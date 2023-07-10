package com.library.main.vo;

import com.library.main.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Role role;
}
