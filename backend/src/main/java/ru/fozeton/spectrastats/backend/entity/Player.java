package ru.fozeton.spectrastats.backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table
public class Player {
    @Id
    private String playerName;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OnlineSession> session = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    private LocalDateTime time = LocalDateTime.now();
}
