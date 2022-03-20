package ru.pronin.study.vkr.tradeBot.strategies.manager;

import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.study.vkr.tradeBot.indicators.StochasticOscillator;
import ru.pronin.study.vkr.tradeBot.indicators.utils.ScalableMap;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class StochasticManager implements IndicatorManager<StochasticOscillator> {

    private final int ScalableMapDepth = 2;

    private final ScalableMap<ZonedDateTime, BigDecimal> values = new ScalableMap<>(ScalableMapDepth);
    private final ScalableMap<ZonedDateTime, BigDecimal> emaValues = new ScalableMap<>(ScalableMapDepth);

    @Override
    public boolean isTimeToBuy(StochasticOscillator indicator, CustomCandle candle) {
        return  isEnoughInformation()
                && values.getValues().get(1).compareTo(values.getValues().get(0)) > 0
                && emaValues.getValues().get(0).compareTo(values.getValues().get(0)) > 0
                && values.getValues().get(1).compareTo(emaValues.getValues().get(1)) > 0
                && new BigDecimal(20L).compareTo(values.getValues().get(1)) >= 0;
    }

    private boolean isEnoughInformation() {
        return values.getSize() == ScalableMapDepth && emaValues.getSize() == ScalableMapDepth;
    }

    @Override
    public void processIndicatorValues(StochasticOscillator indicator) {
        values.addValue(indicator.getValue().getKey(), indicator.getValue().getValue());
        emaValues.addValue(indicator.getEmaValue().getKey(), indicator.getEmaValue().getValue());
    }

    @Override
    public boolean isTimeToSell(StochasticOscillator indicator, CustomCandle candle) {
        return isEnoughInformation()
                && values.getValues().get(0).compareTo(values.getValues().get(1)) > 0
                && emaValues.getValues().get(1).compareTo(values.getValues().get(1)) >= 0
                && values.getValues().get(1).compareTo(new BigDecimal(80L)) >= 0;
    }

    @Override
    public IndicatorName getName() {
        return IndicatorName.STOCHASTIC_OSCILLATOR;
    }
}
