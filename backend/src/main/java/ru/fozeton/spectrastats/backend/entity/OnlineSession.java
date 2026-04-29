package ru.fozeton.spectrastats.backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table
public class OnlineSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_name")
    private Player player;

    @Setter(AccessLevel.NONE)
    private String entryTime = LocalDateTime.now().toString();
    private String exitTime;
}
