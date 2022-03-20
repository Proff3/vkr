package ru.pronin.study.vkr.tradeBot.indicators;

import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.study.vkr.tradeBot.indicators.utils.Pair;
import ru.pronin.study.vkr.tradeBot.indicators.utils.ScalableMap;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;

public class StochasticOscillator implements Indicator {

    private final int DEPTH;
    private final SMA SMA;
    private final BigDecimal MULTIPLIER = new BigDecimal(100);

    private BigDecimal max;
    private BigDecimal min;
    private Pair<ZonedDateTime, BigDecimal> value;
    private final ScalableMap<ZonedDateTime, CustomCandle> candleMap;
    private boolean isOver = false;
    private final ScalableMap<ZonedDateTime, BigDecimal> numerators;
    private final ScalableMap<ZonedDateTime, BigDecimal> denominators;

    public StochasticOscillator(int depth, int emaDepth, int smooth) {
        DEPTH = depth;
        SMA = new SMA(emaDepth);
        candleMap = new ScalableMap<>(depth);
        numerators = new ScalableMap<>(smooth);
        denominators = new ScalableMap<>(smooth);
    }

    public StochasticOscillator(int depth, int emaDepth) {
        DEPTH = depth;
        SMA = new SMA(emaDepth);
        candleMap = new ScalableMap<>(depth);
        numerators = new ScalableMap<>(1);
        denominators = new ScalableMap<>(1);
    }

    @Override
    public void addCandle(CustomCandle candle) {
        if(max == null || min == null) {
            updateLimits(candle);
            calculate(candle);
        } else {
            calculate(candle);
        }
    }

    @Override
    public Boolean isEnoughInformation() {
        return candleMap.getSize() >= DEPTH && SMA.isEnoughInformation();
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

    private void calculate(CustomCandle candle) {
        candleMap.addValue(candle.getTime(), candle);
        updateLimits(candle);
        value = new Pair<>(candle.getTime(), calculateCurrentValue(candle));
        CustomCandle candleWithCalculatedValue = CustomCandle.getCandleWithNewCloseValue(candle, value.getValue());
        SMA.addCandle(candleWithCalculatedValue);
    }

    private void updateLimits(CustomCandle candle) {
        BigDecimal currentMax = candleMap.getValues().stream().map(CustomCandle::getH).max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
        BigDecimal currentMin = candleMap.getValues().stream().map(CustomCandle::getL).min(BigDecimal::compareTo).orElse(new BigDecimal(1_000_000));
        max = candle.getH().compareTo(currentMax) > 0 ? candle.getH() : currentMax;
        min = candle.getL().compareTo(currentMin) < 0 ? candle.getL() : currentMin;
    }

    private BigDecimal calculateCurrentValue(CustomCandle candle) {
        BigDecimal numerator = candle.getC().subtract(min);
        numerators.addValue(candle.getTime(), numerator);
        BigDecimal numeratorSmoothValue = numerators.getValues().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal denominator = max.compareTo(min) == 0 ? BigDecimal.ONE : max.subtract(min);
        denominators.addValue(candle.getTime(), denominator);
        BigDecimal denominatorSmoothValue = denominators.getValues().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return MULTIPLIER.multiply(numeratorSmoothValue.divide(denominatorSmoothValue, 4, RoundingMode.HALF_UP));
    }

    public Pair<ZonedDateTime, BigDecimal> getValue() {
        return value;
    }

    public Pair<ZonedDateTime, BigDecimal> getEmaValue() {
        return SMA.getCurrentValue();
    }
}
