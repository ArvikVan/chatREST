package com.example.chatrest.controller;

import com.example.chatrest.entity.PersonEntity;
import com.example.chatrest.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author ArvikV
 * @version 1.0
 * @since 17.02.2022
 * Это контроллер - тут ЛОГИКИ быть не должно, сюда вызываются методы из СЕРВИСНОГО СЛОЯ
 */
@RestController
@RequestMapping("/persons")
public class PersonController {
    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
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
}
