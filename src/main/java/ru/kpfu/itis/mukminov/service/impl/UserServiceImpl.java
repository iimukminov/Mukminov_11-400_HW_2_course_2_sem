package ru.kpfu.itis.mukminov.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.mukminov.dto.UserDto;
import ru.kpfu.itis.mukminov.model.Role;
import ru.kpfu.itis.mukminov.model.User;
import ru.kpfu.itis.mukminov.repository.RoleRepository;
import ru.kpfu.itis.mukminov.repository.UserRepository;
import ru.kpfu.itis.mukminov.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepositoryJpa;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepositoryJpa,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository,
                           EmailService emailService) {
        this.userRepositoryJpa = userRepositoryJpa;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
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
    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        User user = userRepositoryJpa.findById(id).orElseThrow();
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
        user.setVerified(false);
        user.setVerificationCode(UUID.randomUUID().toString());

        User savedUser = userRepositoryJpa.saveAndFlush(user);

        emailService.sendVerificationEmail(savedUser);

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
        User savedUser = userRepositoryJpa.save(user);
        return new UserDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getLastname(),
                savedUser.getEmail()
        );
    }

    @Override
    @Transactional
    public boolean verifyUser(String code) {
        Optional<User> userOpt = userRepositoryJpa.findByVerificationCode(code);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setVerified(true);
            user.setVerificationCode(null);
            userRepositoryJpa.save(user);
            return true;
        }
        return false;
    }
}