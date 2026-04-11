package ru.kpfu.itis.mukminov.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.kpfu.itis.mukminov.service.impl.HelloService;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HelloService helloService;

    @Test
    void hello_ShouldReturnGreeting() throws Exception {
        String name = "Ilya";
        given(helloService.sayHello(name)).willReturn("Hello, Ilya");

        mockMvc.perform(get("/hello")
                        .param("da", name)
                        .with(user("testUser").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, Ilya"));
    }
}