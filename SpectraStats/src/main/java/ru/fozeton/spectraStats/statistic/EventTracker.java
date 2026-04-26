package ru.fozeton.spectraStats.statistic;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EventTracker<T> {
    @Getter
    private final Map<String, List<T>> storage = new ConcurrentHashMap<>();

    public void add(String player, T data) {
        storage.computeIfAbsent(player, d -> new ArrayList<>()).add(data);
    }

    public void clear() {
        storage.clear();
    }
}