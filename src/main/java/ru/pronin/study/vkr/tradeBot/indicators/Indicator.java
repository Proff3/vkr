package ru.pronin.study.vkr.tradeBot.indicators;

import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomCandle;

public interface Indicator {
    void addCandle(CustomCandle candle);
    Boolean isEnoughInformation();
    Boolean isOver();
    int getIndicatorDepth();
    void setOver();
}
