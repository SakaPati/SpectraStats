package ru.fozeton.spectrastats.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fozeton.spectrastats.backend.entity.PlayerStats;
import ru.fozeton.spectrastats.backend.exceptions.StatisticNotFoundInPlayer;
import ru.fozeton.spectrastats.backend.exceptions.UnknownStatisticType;
import ru.fozeton.spectrastats.backend.repository.PlayerStatsRepo;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final PlayerStatsRepo statsRepo;

    public PlayerStats getStatistic(String player) {
        return statsRepo.findById(player)
                .orElseThrow(() -> new StatisticNotFoundInPlayer("Statistic not found in: " + player));
    }

    public Object getStatisticByType(String player, String type) {
        PlayerStats playerStats = statsRepo.findById(player)
                .orElseThrow(() -> new StatisticNotFoundInPlayer("Statistic not found in: " + player));

        return switch (type.toLowerCase()) {
            case "world" -> playerStats.getWorld();
            case "survival" -> playerStats.getSurvival();
            case "interact" -> playerStats.getInteract();
            case "combat" -> playerStats.getCombat();
            default -> throw new UnknownStatisticType("Unknown type: " + type);
        };
    }
}
