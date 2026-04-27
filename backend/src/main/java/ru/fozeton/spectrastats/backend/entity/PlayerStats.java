package ru.fozeton.spectrastats.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Data
@Entity
@Table
public class PlayerStats {
    @Id
    private String id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Player player;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Integer> world;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Integer> survival;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Integer> interact;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Integer> combat;
}
