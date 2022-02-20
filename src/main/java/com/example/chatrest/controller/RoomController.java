package com.example.chatrest.controller;

import com.example.chatrest.entity.Message;
import com.example.chatrest.entity.Room;
import com.example.chatrest.repository.RoomRepo;
import com.example.chatrest.service.Patch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author ArvikV
 * @version 1.0
 * @since 18.02.2022
 */
@RestController
@RequestMapping("/rooms")
public class RoomController {
    private static final String API = "http://localhost:8081/rooms/";
    private static final String API_ID = "http://localhost:8081/rooms/{id}";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RoomRepo roomRepo;

    @GetMapping("/")
    public List<Room> findAll() {
        return StreamSupport.stream(
                this.roomRepo.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @PostMapping("/")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        var roomName = room.getName();
        if (roomName == null) {
            throw new NullPointerException("The Room must have a NOMBRE");
        }
        return new ResponseEntity<>(
                this.roomRepo.save(room),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> updateRoom(@RequestBody Room room) {
        restTemplate.put(API, room);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable int id) {
        restTemplate.delete(API_ID, id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/")
    public Room patch(@RequestBody Room room) throws InvocationTargetException,
            IllegalAccessException {
        Room current = roomRepo.findById(room.getId()).orElse(null);
        Patch.patchAll(room, current);
        roomRepo.save(room);
        return current;
    }
}
