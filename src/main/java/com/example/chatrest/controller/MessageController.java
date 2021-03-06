package com.example.chatrest.controller;

import com.example.chatrest.entity.Message;
import com.example.chatrest.repository.MessageRepo;
import com.example.chatrest.service.Patch;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author ArvikV
 * @version 1.0
 * @since 17.02.2022
 * Valid Она указывает, что предварительно перед тем как мы сможем работать с моделью данные будут
 * проходить валидацию согласно аннотациям валидации, прописанным в модели.
 */
@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageRepo messages;

    public MessageController(MessageRepo messages) {
        this.messages = messages;
    }

    @GetMapping("/")
    public ResponseEntity<List<Message>> findAllMessages() {
        return new ResponseEntity<>(StreamSupport.stream(
                this.messages.findAll().spliterator(), false
        ).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@Valid @PathVariable int id) {
        var message = this.messages.findById(id);
        return new ResponseEntity<Message>(
                message.orElse(new Message()),
                message.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@Valid @RequestBody Message message) {
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
    public ResponseEntity<Message> create(@Valid @RequestBody Message message) {
        var messageText = message.getText();
        if (messageText == null) {
            throw new NullPointerException("The text in message must be not empty");
        }
        return new ResponseEntity<Message>(
                this.messages.save(message),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/")
    public Message patch(@Valid @RequestBody Message message) throws InvocationTargetException,
            IllegalAccessException {
        Message current = messages.findById(message.getId()).orElse(null);
        Patch.patchAll(message, current);
        messages.save(message);
        return current;
    }
}
