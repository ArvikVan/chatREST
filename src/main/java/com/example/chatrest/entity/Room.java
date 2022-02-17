package com.example.chatrest.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private String name;
    @ManyToMany
    @JoinTable(name = "person_room")
    private List<PersonEntity> persons = new ArrayList<>();

    public Room() {
    }

    public static Room of(int id, String name, List<PersonEntity> person) {
        Room room = new Room();
        room.id = id;
        room.name = name;
        room.persons = person;
        return room;
    }

    public void addPerson(PersonEntity author) {
        persons.add(author);
    }

    public List<PersonEntity> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonEntity> persons) {
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
