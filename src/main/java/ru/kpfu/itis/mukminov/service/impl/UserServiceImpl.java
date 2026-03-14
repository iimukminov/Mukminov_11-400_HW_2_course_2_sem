package ru.kpfu.itis.mukminov.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.mukminov.dto.UserDto;
import ru.kpfu.itis.mukminov.model.Role;
import ru.kpfu.itis.mukminov.model.User;
import ru.kpfu.itis.mukminov.repository.RoleRepository;
import ru.kpfu.itis.mukminov.repository.UserRepository;
import ru.kpfu.itis.mukminov.repository.UserRepositoryHibernate;
import ru.kpfu.itis.mukminov.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepositoryHibernate userRepositoryHibernate;
    private final UserRepository userRepositoryJpa;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepositoryHibernate userRepositoryHibernate,
                           UserRepository userRepositoryJpa,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.userRepositoryHibernate = userRepositoryHibernate;
        this.userRepositoryJpa = userRepositoryJpa;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Optional<Role> existingRole = roleRepository.findByName("ROLE_USER");
        Role userRole;

        if (existingRole.isPresent()) {
            userRole = existingRole.get();
        } else {
            userRole = new Role();
            userRole.setName("ROLE_USER");
            userRole = roleRepository.save(userRole);
        }

        user.setRoles(List.of(userRole));

        User savedUser = userRepositoryJpa.saveAndFlush(user);

        return new UserDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getLastname(),
                savedUser.getEmail()
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