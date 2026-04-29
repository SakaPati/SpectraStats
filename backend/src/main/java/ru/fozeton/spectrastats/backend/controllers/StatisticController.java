package ru.fozeton.spectrastats.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fozeton.spectrastats.backend.entity.PlayerStats;
import ru.fozeton.spectrastats.backend.service.StatisticService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statistic/{player}")
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping
    public ResponseEntity<PlayerStats> statistic(@PathVariable String player) {
        return ResponseEntity.ok(statisticService.getStatistic(player));
    }

    @GetMapping("/{type}")
    public ResponseEntity<Object> typeStatistic(@PathVariable String player, @PathVariable String type) {
        return ResponseEntity.ok(statisticService.getStatisticByType(player, type));
    }
}
