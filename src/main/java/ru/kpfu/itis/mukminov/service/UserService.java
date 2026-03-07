package ru.kpfu.itis.mukminov.service;

import ru.kpfu.itis.mukminov.dto.UserDto;
import ru.kpfu.itis.mukminov.model.User;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();
    UserDto findById(Long id);
    UserDto save(User user);
    void delete(Long id);
    UserDto update(User user);
}