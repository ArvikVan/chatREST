package com.example.chatrest.controller;

import com.example.chatrest.entity.Role;
import com.example.chatrest.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author ArvikV
 * @version 1.0
 * @since 18.02.2022
 */
@RestController
@RequestMapping("/roles")
public class RoleController {
    private static final String API = "http://localhost:8081/roles/";
    private static final String API_ID = "http://localhost:8081/roles/{id}";
    private final RoleRepo roleRepo;

    @Autowired
    private RestTemplate restTemplate;

    public RoleController(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @GetMapping("/")
    public List<Role> findAll() {
        return StreamSupport.stream(
                this.roleRepo.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @PostMapping("/")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        var nameOfAuthority = role.getAuthority();
        if (nameOfAuthority == null) {
            throw new NullPointerException("Role must be with somename");
        }
        return new ResponseEntity<>(
                this.roleRepo.save(role),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> updateRole(@RequestBody Role role) {
        restTemplate.put(API, role);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable int id) {
        restTemplate.delete(API_ID, id);
        return ResponseEntity.ok().build();
    }
}
