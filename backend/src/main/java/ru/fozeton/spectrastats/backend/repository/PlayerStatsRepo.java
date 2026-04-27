package ru.fozeton.spectrastats.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fozeton.spectrastats.backend.entity.PlayerStats;

public interface PlayerStatsRepo extends JpaRepository<PlayerStats, String> {
}
