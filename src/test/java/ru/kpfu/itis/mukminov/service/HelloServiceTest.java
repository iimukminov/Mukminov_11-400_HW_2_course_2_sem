package ru.kpfu.itis.mukminov.service;

import org.junit.jupiter.api.Test;
import ru.kpfu.itis.mukminov.service.impl.HelloService;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloServiceTest {

    private final HelloService helloService = new HelloService();

    @Test
    void sayHello_ShouldReturnCorrectGreeting() {
        String name = "Ilya";

        String result = helloService.sayHello(name);

        assertEquals("Hello, Ilya", result);
    }

    @Test
    void sayHello_WithNull_ShouldReturnHelloNull() {
        String result = helloService.sayHello(null);
        assertEquals("Hello, null", result);
    }
}