package com.example.chatrest.repository;

import com.example.chatrest.entity.Room;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ArvikV
 * @version 1.0
 * @since 17.02.2022
 */
public interface RoomRepo extends CrudRepository<Room, Integer> {
}
