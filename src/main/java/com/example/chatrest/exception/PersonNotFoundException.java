package com.example.chatrest.exception;

/**
 * @author ArvikV
 * @version 1.0
 * @since 17.02.2022
 */
public class PersonNotFoundException extends Exception {
    public PersonNotFoundException(String message) {
        super(message);
    }
}
