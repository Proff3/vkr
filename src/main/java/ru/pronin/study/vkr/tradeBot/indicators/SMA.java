package ru.pronin.study.vkr.tradeBot.indicators;

import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.study.vkr.tradeBot.indicators.utils.Pair;
import ru.pronin.study.vkr.tradeBot.indicators.utils.ScalableMap;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;

public class SMA implements Indicator {

    private final int DEPTH;
    private final ScalableMap<ZonedDateTime, CustomCandle> candleMap;

    private Pair<ZonedDateTime, BigDecimal> currentValue;
    private boolean isOver;

    public SMA(int depth) {
        DEPTH = depth;
        candleMap = new ScalableMap<>(depth);
    }

    @Override
    public void addCandle(CustomCandle candle) {
        candleMap.addValue(candle.getTime(), candle);
        double currentValueInDouble = candleMap.getValues()
                .stream()
                .collect(Collectors.averagingDouble(c -> c.getC().doubleValue()));
        BigDecimal value = new BigDecimal(currentValueInDouble).setScale(4, RoundingMode.HALF_UP);
        currentValue = new Pair<>(candle.getTime(), value);
    }

    @Override
    public Boolean isEnoughInformation() {
        return candleMap.getSize() > DEPTH;
    }

    @Override
    public Boolean isOver() {
        return isOver;
    }

    @Override
    public int getIndicatorDepth() {
        return DEPTH;
    }

    @Override
    public void setOver() {
        isOver = true;
    }

    public Pair<ZonedDateTime, BigDecimal> getCurrentValue() {
        return currentValue;
    }
}
