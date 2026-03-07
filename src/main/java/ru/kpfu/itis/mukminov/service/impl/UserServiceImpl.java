package ru.kpfu.itis.mukminov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.mukminov.dto.UserDto;
import ru.kpfu.itis.mukminov.model.User;
import ru.kpfu.itis.mukminov.repository.UserRepository;
import ru.kpfu.itis.mukminov.repository.UserRepositoryHibernate;
import ru.kpfu.itis.mukminov.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepositoryHibernate userRepositoryHibernate;
    private final UserRepository userRepositoryJpa;

    public UserServiceImpl(UserRepositoryHibernate userRepositoryHibernate,  UserRepository userRepositoryJpa) {
        this.userRepositoryHibernate = userRepositoryHibernate;
        this.userRepositoryJpa = userRepositoryJpa;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
//        List<User> users = userRepositoryHibernate.findAll();
        List<User> usersJpa = userRepositoryJpa.findAll();

        return usersJpa.stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getName(),
                        user.getLastname(),
                        user.getEmail()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto findById(Long id) {
        User user = userRepositoryJpa.findById(id).get();
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getLastname(),
                user.getEmail()
        );
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepositoryJpa.deleteById(id);
    }

    @Override
    @Transactional
    public UserDto save(User user) {
        User user1 = userRepositoryJpa.saveAndFlush(user);
        return new UserDto(
                user1.getId(),
                user1.getName(),
                user1.getLastname(),
                user1.getEmail()
        );
    }

    @Override
    @Transactional
    public UserDto update(User user) {
        User user1 = userRepositoryJpa.save(user);
        return new UserDto(
                user1.getId(),
                user1.getName(),
                user1.getLastname(),
                user1.getEmail()
        );
    }
}