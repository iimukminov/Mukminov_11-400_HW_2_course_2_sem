package ru.kpfu.itis.mukminov.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kpfu.itis.mukminov.model.Role;
import ru.kpfu.itis.mukminov.model.User;
import ru.kpfu.itis.mukminov.repository.RoleRepository;
import ru.kpfu.itis.mukminov.repository.UserRepository;
import ru.kpfu.itis.mukminov.service.impl.EmailService;
import ru.kpfu.itis.mukminov.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepositoryJpa;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private RoleRepository roleRepository;
    @Mock private EmailService emailService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void save_ShouldEncodePasswordAndSendEmail() {
        User user = new User();
        user.setName("Ivan");
        user.setPassword("rawPassword");
        user.setEmail("test@test.com");

        Role role = new Role();
        role.setName("ROLE_USER");

        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));
        when(userRepositoryJpa.saveAndFlush(any(User.class))).thenReturn(user);

        userService.save(user);


        assertEquals("encodedPassword", user.getPassword());

        assertNotNull(user.getVerificationCode());
        verify(emailService).sendVerificationEmail(user);
    }

    @Test
    void verifyUser_Success() {
        User user = new User();
        user.setVerificationCode("secret-code");
        user.setVerified(false);

        when(userRepositoryJpa.findByVerificationCode("secret-code")).thenReturn(Optional.of(user));

        boolean result = userService.verifyUser("secret-code");

        assertTrue(result);
        assertTrue(user.getVerified());
        assertNull(user.getVerificationCode());
        verify(userRepositoryJpa).save(user);
    }

    @Test
    void verifyUser_InvalidCode_ShouldReturnFalse() {
        when(userRepositoryJpa.findByVerificationCode("wrong")).thenReturn(Optional.empty());

        boolean result = userService.verifyUser("wrong");

        assertFalse(result);
        verify(userRepositoryJpa, never()).save(any());
    }
}