package ru.pronin.study.vkr.tradeBot.strategies;

import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.study.vkr.tradeBot.indicators.Indicator;

import java.util.List;

public interface Strategy {
    void addCandle(CustomCandle candle);
    List<Indicator> getIndicators();
    boolean isTimeToBuy();
    boolean isTimeToSell();
}
