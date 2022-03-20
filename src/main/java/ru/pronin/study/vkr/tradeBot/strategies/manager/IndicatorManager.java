package ru.pronin.study.vkr.tradeBot.strategies.manager;

import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.study.vkr.tradeBot.indicators.Indicator;

public interface IndicatorManager<T extends Indicator> {
     boolean isTimeToBuy(T indicator, CustomCandle candle);
     boolean isTimeToSell(T indicator, CustomCandle candle);
     void processIndicatorValues(T indicator);
     IndicatorName getName();
}
