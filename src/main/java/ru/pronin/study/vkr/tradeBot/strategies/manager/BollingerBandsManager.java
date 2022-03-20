package ru.pronin.study.vkr.tradeBot.strategies.manager;

import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.study.vkr.tradeBot.indicators.BollingerBands;
import ru.pronin.study.vkr.tradeBot.indicators.utils.ScalableMap;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;

public class BollingerBandsManager implements IndicatorManager<BollingerBands> {

    private final int ScalableMapDepth = 2;
    private final BigDecimal dispersion;

    public BollingerBandsManager(BigDecimal dispersionInPercents) {
        this.dispersion = dispersionInPercents
                .setScale(2, RoundingMode.HALF_UP)
                .divide(new BigDecimal(100L), RoundingMode.HALF_UP);
    }

    private final ScalableMap<ZonedDateTime, BigDecimal> ML = new ScalableMap<>(ScalableMapDepth);
    private final ScalableMap<ZonedDateTime, BigDecimal> HL = new ScalableMap<>(ScalableMapDepth);
    private final ScalableMap<ZonedDateTime, BigDecimal> LL = new ScalableMap<>(ScalableMapDepth);

    @Override
    public boolean isTimeToBuy(BollingerBands indicator, CustomCandle candle) {
        BigDecimal closedValue = candle.getC().setScale(5, RoundingMode.HALF_UP);
        BigDecimal delta = candle.getC().setScale(5, RoundingMode.HALF_UP).multiply(dispersion);
        BigDecimal lowValue = indicator.getLLValue();
        return closedValue.add(delta).compareTo(lowValue) <= 0
                && closedValue.subtract(delta).compareTo(closedValue) >= 0;
    }

    @Override
    public boolean isTimeToSell(BollingerBands indicator, CustomCandle candle) {
        BigDecimal closedValue = candle.getC().setScale(5, RoundingMode.HALF_UP);
        BigDecimal delta = candle.getC().setScale(5, RoundingMode.HALF_UP).multiply(dispersion);
        BigDecimal highValue = indicator.getHLValue();
        return closedValue.add(delta).compareTo(highValue) <= 0
                && closedValue.subtract(delta).compareTo(closedValue) >= 0;
    }

    @Override
    public void processIndicatorValues(BollingerBands indicator) {
        ZonedDateTime time = indicator.getLastZonedDateTime();
        ML.addValue(time, indicator.getMLValue());
        HL.addValue(time, indicator.getHLValue());
        LL.addValue(time, indicator.getLLValue());
    }

    @Override
    public IndicatorName getName() {
        return IndicatorName.BOLLINGER_BANDS;
    }
}
