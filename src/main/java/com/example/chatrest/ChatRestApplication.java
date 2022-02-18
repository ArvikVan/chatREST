package com.example.chatrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * добавим тут бины для последущего вызова их с помощью @Autowired
 */
@SpringBootApplication
public class ChatRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatRestApplication.class, args);
    }

    /**
     * Spring boot имеет удобный механизм интеграции Rest сервисов.
     * RestTemplate - позволяет осуществлять вывозы стороннего REST API.
     * @return на выходе экземпляр класса
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
