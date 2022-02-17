package com.example.chatrest.controller;

import com.example.chatrest.entity.Message;
import com.example.chatrest.entity.PersonEntity;
import com.example.chatrest.repository.MessageRepo;
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
 */
@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageRepo messages;

    public MessageController(MessageRepo messages) {
        this.messages = messages;
    }

    @GetMapping("/")
    public List<Message> findAllMessages() {
        return StreamSupport.stream(
                this.messages.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable int id) {
        var message = this.messages.findById(id);
        return new ResponseEntity<Message>(
                message.orElse(new Message()),
                message.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Message message) {
        messages.save(message);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Message message = new Message();
        message.setId(id);
        this.messages.delete(message);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/")
    public ResponseEntity<Message> create(@RequestBody Message message) {
        return new ResponseEntity<Message>(
                this.messages.save(message),
                HttpStatus.CREATED
        );
    }
}
