package ru.fozeton.spectrastats.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.fozeton.spectrastats.backend.model.statistica.CombatStat;
import ru.fozeton.spectrastats.backend.model.statistica.InteractStat;
import ru.fozeton.spectrastats.backend.model.statistica.SurvivalStat;
import ru.fozeton.spectrastats.backend.model.statistica.WorldStat;

import java.util.Map;

@Getter
@Setter
@Entity
@Table
public class PlayerStats {
    @Id
    @Setter(AccessLevel.NONE)
    private String id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonIgnore
    private Player player;

    @JdbcTypeCode(SqlTypes.JSON)
    private WorldStat world;

    @JdbcTypeCode(SqlTypes.JSON)
    private SurvivalStat survival;

    @JdbcTypeCode(SqlTypes.JSON)
    private InteractStat interact;

    @JdbcTypeCode(SqlTypes.JSON)
    private CombatStat combat;
}
