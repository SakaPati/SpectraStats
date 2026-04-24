package ru.fozeton.spectraStats;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Messages {
    @Getter
    private final List<Message> messages = new ArrayList<>();


    public void addMessage(Message message) {
        messages.add(message);
    }

    public record Message(
            String player,
            String message,
            String time
    ) {}
}