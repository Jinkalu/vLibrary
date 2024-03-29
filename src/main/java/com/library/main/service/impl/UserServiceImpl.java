package com.library.main.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.main.entity.Users;
import com.library.main.exception.ErrorVO;
import com.library.main.exception.ValidationException;
import com.library.main.mappers.UserServiceMapper;
import com.library.main.repo.TokenRepository;
import com.library.main.repo.UserRepository;
import com.library.main.security.jwt.JWTService;
import com.library.main.service.UserService;
import com.library.main.vo.AuthResponse;
import com.library.main.vo.UserRegVO;
import com.library.main.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final TokenRepository tokenRepository;

    @Override
    public void saveUsers(List<UserVO> userVOList) {
        repository.saveAll(userVOList.stream()
                .map(user -> {
                    validateUserByEmail(user.getEmail());
                    Users users = mapToUser(user);
                    users.setPassword(encoder.encode(user.getPassword()));
                    return users;
                }).collect(Collectors.toList()));
    }

    @Override
    public UserVO findUserById(Long userId) {
        Optional<Users> user = repository.findById(userId);
        if (user.isPresent()) {
            return mapToUserVO(user.get());
        }
        throw new ValidationException(List.of(new ErrorVO("NOT_FOUND",
                "USER_NOT_FOUND")));
    }

    @Override
    public AuthResponse addUser(UserVO userVO) {
        validateUserByEmail(userVO.getEmail());
        Users users = mapToUser(userVO);
        users.setPassword(encoder.encode(users.getPassword()));
        var user = repository.save(users);
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        tokenRepository.save(UserServiceMapper.saveUserToken(user, accessToken));
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    @Override
    public List<UserVO> listAllUsers() {
        return repository.findAll().stream()
                .map(this::mapToUserVO)
                .collect(Collectors.toList());
    }


    private void validateUserByEmail(String email) {
        if (repository.findByEmail(email).isPresent()) {
            throw new ValidationException(List.of(new ErrorVO("USER_EXIST",
                    "USER WITH EMAIL " + email + " ALREADY EXISTS")));
        }
    }


    private UserVO mapToUserVO(Users user) {
        return modelMapper.map(user, UserVO.class);
    }

    private Users mapToUser(UserVO userVO) {
        return modelMapper.map(userVO, Users.class);
    }

}
