package com.example.bibliotecatecedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BibliotecateceduApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibliotecateceduApplication.class, args);
    }
}