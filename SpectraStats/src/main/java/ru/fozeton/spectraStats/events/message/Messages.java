package ru.fozeton.spectraStats;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Messages {
    @Getter
    private final List<Messages> messages = new ArrayList<>();

    public Messages(String player, String message, String time) {
    }


    public void addMessage(String player, String message, String time) {
        messages.add(new Messages(player, message, time));
    }
}