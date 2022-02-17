package com.example.chatrest.repository;

import com.example.chatrest.entity.Message;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ArvikV
 * @version 1.0
 * @since 17.02.2022
 */
public interface MessageRepo extends CrudRepository<Message, Integer> {
}
