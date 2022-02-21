package com.example.chatrest.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ArvikV
 * @version 1.0
 * @since 17.02.2022
 */
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "RoomName must be not null value")
    private String name;
    @ManyToMany
    @JoinTable(name = "person_room")
    private Set<PersonEntity> persons = new HashSet<>();

    public Room() {
    }

    public static Room of(int id, String name, Set<PersonEntity> person) {
        Room room = new Room();
        room.id = id;
        room.name = name;
        room.persons = person;
        return room;
    }

    public void addPerson(PersonEntity author) {
        persons.add(author);
    }

    public Set<PersonEntity> getPersons() {
        return persons;
    }

    public void setPersons(Set<PersonEntity> persons) {
        this.persons = persons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
