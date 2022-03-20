package ru.pronin.study.vkr.tradeBot.indicators.utils;

import java.util.List;
import java.util.TreeMap;

public class ScalableMap<K extends Comparable<? super K>,V> {

    private final int DEPTH;
    private final TreeMap<K, V> map = new TreeMap<>();

    public ScalableMap(int depth) {
        DEPTH = depth;
    }

    public void addValue(K key, V value) {
        map.put(key, value);
        if (map.size() > DEPTH) {
            map.pollFirstEntry();
        }
    }

    public List<V> getValues() {
        return map.values().stream().toList();
    }

    public int getSize() {
        return map.size();
    }
}
