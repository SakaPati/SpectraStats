package ru.fozeton.spectrastats.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fozeton.spectrastats.backend.entity.Player;

import java.util.Optional;

public interface PlayerRepo extends JpaRepository<Player, Long> {
    Optional<Player> findByPlayerName(String playerName);
}
