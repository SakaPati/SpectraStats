package ru.fozeton.spectrastats.backend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fozeton.spectrastats.backend.entity.OnlineSession;
import ru.fozeton.spectrastats.backend.entity.Player;
import ru.fozeton.spectrastats.backend.entity.PlayerStats;
import ru.fozeton.spectrastats.backend.exceptions.PlayerNotFound;
import ru.fozeton.spectrastats.backend.model.PlayerStatistic;
import ru.fozeton.spectrastats.backend.repository.OnlineRepo;
import ru.fozeton.spectrastats.backend.repository.PlayerRepo;
import ru.fozeton.spectrastats.backend.repository.PlayerStatsRepo;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PlayersService {
    private final PlayerRepo playerRepo;
    private final OnlineRepo onlineRepo;
    private final PlayerStatsRepo statsRepo;

    @Transactional
    public void connect(String playerName) {
        Player player = playerRepo.findByPlayerName(playerName)
                .orElseGet(() -> {
                    Player regPlayer = new Player();
                    PlayerStats stats = new PlayerStats();
                    regPlayer.setPlayerName(playerName);
                    stats.setPlayer(regPlayer);
                    regPlayer.setStatistic(stats);
                    playerRepo.save(regPlayer);

                    return regPlayer;
                });

        OnlineSession session = new OnlineSession();
        session.setPlayer(player);
        onlineRepo.save(session);
    }

    @Transactional
    public void disconnect(String playerName) {
        Player player = playerRepo.findByPlayerName(playerName)
                .orElseThrow(() -> new PlayerNotFound("Player not found"));
        OnlineSession session = player.getSession().getLast();
        session.setExitTime(LocalDateTime.now().toString());
        onlineRepo.save(session);
    }

    public void updateStats(PlayerStatistic statistic) {
        PlayerStats stats = statsRepo.findById(statistic.player())
                .orElseThrow(() -> new PlayerNotFound("Player not found"));
        stats.setWorld(statistic.world());
        stats.setSurvival(statistic.survival());
        stats.setInteract(statistic.interact());
        stats.setCombat(statistic.combat());
        statsRepo.save(stats);
    }
}
