package ru.fozeton.spectrastats.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fozeton.spectrastats.backend.entity.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerRepo extends JpaRepository<Player, String> {
    Optional<Player> findByPlayerName(String playerName);

    List<Player> findFirst10ByPlayerNameContaining(String nick);
}
