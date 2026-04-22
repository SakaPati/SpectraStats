package ru.fozeton.spectrastats.backend.exceptions;

public class PlayerNotFound extends RuntimeException {
    public PlayerNotFound(String message) {
        super(message);
    }
}
