package ru.fozeton.spectrastats.backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class OnlineSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_name")
    private Player player;

    @Setter(AccessLevel.NONE)
    private Long entryTime = System.currentTimeMillis();
    private Long exitTime;
}
