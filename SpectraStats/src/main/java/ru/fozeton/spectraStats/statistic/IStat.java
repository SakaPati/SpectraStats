package ru.fozeton.spectraStats.statistic;

public interface IStat<T> {
    boolean isSystem();
    String name();
    T getStat();
    Class<?> getType();
}
