package ru.kpfu.itis.mukminov.service;

import ru.kpfu.itis.mukminov.dto.UserDto;
import java.util.List;

public interface UserService {
    List<UserDto> findAll();
}