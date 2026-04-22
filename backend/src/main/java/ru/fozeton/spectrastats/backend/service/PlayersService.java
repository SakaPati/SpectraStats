package ru.fozeton.spectrastats.backend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fozeton.spectrastats.backend.entity.OnlineSession;
import ru.fozeton.spectrastats.backend.entity.Player;
import ru.fozeton.spectrastats.backend.exceptions.PlayerNotFound;
import ru.fozeton.spectrastats.backend.repository.OnlineRepo;
import ru.fozeton.spectrastats.backend.repository.PlayerRepo;

@RequiredArgsConstructor
@Service
public class PlayersService {
    private final PlayerRepo playerRepo;
    private final OnlineRepo onlineRepo;

    @Transactional
    public void connect(String playerName) {
        Player player = playerRepo.findByPlayerName(playerName)
                .orElseGet(() -> {
                    Player regPlayer = new Player();
                    regPlayer.setPlayerName(playerName);
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
        session.setExitTime(System.currentTimeMillis());
        onlineRepo.save(session);
    }
}
