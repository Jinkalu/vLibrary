package com.library.main.service.impl;

import com.library.main.entity.Users;
import com.library.main.exception.ErrorVO;
import com.library.main.exception.ValidationException;
import com.library.main.repo.UserRepository;
import com.library.main.service.UserService;
import com.library.main.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;

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
    public void addUser(UserVO userVO) {
        validateUserByEmail(userVO.getEmail());
        Users users = mapToUser(userVO);
        users.setPassword(encoder.encode(users.getPassword()));
        repository.save(users);
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
