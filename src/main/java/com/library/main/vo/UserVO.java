package com.library.main.vo;

import com.library.main.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(name = "email",required = true,type = "String",description = "user's email address")
    private String email;
    private Role role;
}
