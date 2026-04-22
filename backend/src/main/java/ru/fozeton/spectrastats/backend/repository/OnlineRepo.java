package ru.fozeton.spectrastats.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fozeton.spectrastats.backend.entity.OnlineSession;

public interface OnlineRepo extends JpaRepository<OnlineSession, Long> {
}
