package ru.fozeton.spectrastats.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fozeton.spectrastats.backend.service.PlayersService;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
public class EventsController {
    private final PlayersService playersService;

    @PostMapping("/connect/{player}")
    public void connect(@PathVariable String player) {
        playersService.connect(player);
    }
    @PostMapping("/disconnect/{player}")
    public void disconnect(@PathVariable String player) {
        playersService.disconnect(player);
    }
}
