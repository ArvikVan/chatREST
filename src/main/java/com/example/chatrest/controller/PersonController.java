package com.example.chatrest.controller;

import com.example.chatrest.entity.PersonEntity;
import com.example.chatrest.exception.PersonExistExceprion;
import com.example.chatrest.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author ArvikV
 * @version 1.0
 * @since 17.02.2022
 * Это контроллер - тут ЛОГИКИ быть не должно, сюда вызываются методы из СЕРВИСНОГО СЛОЯ
 * добавим метод для регистрации sign-up
 */
@RestController
@RequestMapping("/persons")
public class PersonController {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(PersonController.class.getSimpleName());
    private final ObjectMapper objectMapper;
    private final PersonRepository personRepository;
    private BCryptPasswordEncoder encoder;

    public PersonController(ObjectMapper objectMapper,
                            PersonRepository personRepository, BCryptPasswordEncoder encoder) {
        this.objectMapper = objectMapper;
        this.personRepository = personRepository;
        this.encoder = encoder;
    }

    /**
     * регим персону
     * @param person персон
     */
    @PostMapping("/sign-up")
    public void signUp(@RequestBody PersonEntity person) {
        var username = person.getUsername();
        var password = person.getPassword();
        if (username == null || password == null) {
            throw new NullPointerException("Username and password mustn't be empty");
        }
        person.setPassword(encoder.encode(person.getPassword()));
        personRepository.save(person);
    }

    @PostMapping("/")
    public ResponseEntity<PersonEntity> create(@RequestBody PersonEntity person) {
        return new ResponseEntity<PersonEntity>(
                this.personRepository.save(person),
                HttpStatus.CREATED
        );
    }

    /**
     * @return получаем всех пользователей
     */
    @GetMapping("/")
    public List<PersonEntity> findAll() {
        return StreamSupport.stream(
                this.personRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonEntity> findById(@PathVariable int id) {
        var person = this.personRepository.findById(id);
        return new ResponseEntity<PersonEntity>(
                person.orElse(new PersonEntity()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody PersonEntity person) {
        this.personRepository.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        PersonEntity person = new PersonEntity();
        person.setId(id);
        this.personRepository.delete(person);
        return ResponseEntity.ok().build();
    }

    /**
     * обрабатываем исключения только в этом контроллере
     * @param e исключения
     * @param request запросы
     * @param response ответы
     * @throws IOException исключение которое бросаем
     */
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public void exceptionHandler(Exception e, HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", e.getMessage());
                put("type", e.getClass());
            }
        }));
        LOGGER.error(e.getLocalizedMessage());
    }
}
