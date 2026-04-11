package ru.kpfu.itis.mukminov.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.kpfu.itis.mukminov.config.SecurityConfig;
import ru.kpfu.itis.mukminov.service.UserService;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VerificationController.class)
@Import(SecurityConfig.class)
class VerificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    void verify_Success_ShouldRedirectWithVerifiedParam() throws Exception {
        given(userService.verifyUser("good-code")).willReturn(true);

        mockMvc.perform(get("/verification").param("code", "good-code"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?verified=true"));
    }

    @Test
    void verify_Fail_ShouldRedirectWithErrorParam() throws Exception {
        given(userService.verifyUser("bad-code")).willReturn(false);

        mockMvc.perform(get("/verification").param("code", "bad-code"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error=invalid_code"));
    }
}