package com.library.main;

import com.library.main.service.UserService;
import com.library.main.vo.UserVO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static com.library.main.enums.Role.ADMIN;
import static com.library.main.enums.Role.MANAGER;

@EnableWebMvc
@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserService service) {
        return args -> {
            var admin = UserVO.builder()
                    .firstName("Admin")
                    .lastName("Admin")
                    .email("admin@gmail.com")
                    .password("admin123")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: " + service.addUser(admin).getAccessToken());
            var manager = UserVO.builder()
                    .firstName("Manager")
                    .lastName("Manager")
                    .email("manager@gmail.com")
                    .password("manager123")
                    .role(MANAGER)
                    .build();
            System.out.println("Manager token: " + service.addUser(manager).getAccessToken());
        };
    }

}
