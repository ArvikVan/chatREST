package com.example.chatrest.repository;

import com.example.chatrest.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ArvikV
 * @version 1.0
 * @since 17.02.2022
 */
public interface PersonRepository extends CrudRepository<PersonEntity, Integer> {
    PersonEntity findByUsername(String username);
}
