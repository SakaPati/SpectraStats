package ru.fozeton.spectrastats.backend.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import ru.fozeton.spectrastats.backend.model.statistica.CombatStat;
import ru.fozeton.spectrastats.backend.model.statistica.InteractStat;
import ru.fozeton.spectrastats.backend.model.statistica.SurvivalStat;
import ru.fozeton.spectrastats.backend.model.statistica.WorldStat;

public record PlayerStatistic(
    String player,
    @Valid @NotNull WorldStat world,
    @Valid @NotNull SurvivalStat survival,
    @Valid @NotNull InteractStat interact,
    @Valid @NotNull CombatStat combat
) {
}
