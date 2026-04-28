package ru.fozeton.spectrastats.backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table
public class Player {
    @Id
    @Column(name = "id")
    private String playerName;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OnlineSession> session = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "player")
    private PlayerStats statistic = new PlayerStats();

    @Setter(AccessLevel.NONE)
    private LocalDateTime timestamp = LocalDateTime.now();
}
