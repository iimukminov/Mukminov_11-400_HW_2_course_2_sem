package ru.kpfu.itis.mukminov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.mukminov.dto.UserDto;
import ru.kpfu.itis.mukminov.model.User;
import ru.kpfu.itis.mukminov.repository.UserRepository;
import ru.kpfu.itis.mukminov.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getName(),
                        user.getLastname(),
                        user.getEmail()
                ))
                .collect(Collectors.toList());
    }
}