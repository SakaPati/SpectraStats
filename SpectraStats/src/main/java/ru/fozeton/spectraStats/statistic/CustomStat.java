package ru.fozeton.spectraStats.statistic;

import javax.annotation.Nullable;
import java.util.List;

public record CustomStat<T>(String name, List<T> list, Class<T> type) implements IStat<List<T>> {
    @Override
    public boolean isSystem() {
        return false;
    }

    @Override
    @Nullable
    public List<T> getStat() {
        return list;
    }

    @Override
    public Class<T> getType() {
        return type;
    }
}
