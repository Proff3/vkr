package ru.pronin.study.vkr.tradeBot.strategies.max;

import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.study.vkr.tradeBot.indicators.BollingerBands;
import ru.pronin.study.vkr.tradeBot.indicators.Indicator;
import ru.pronin.study.vkr.tradeBot.indicators.StochasticOscillator;
import ru.pronin.study.vkr.tradeBot.strategies.Strategy;

import java.util.List;

public class MaxStrategy implements Strategy {

    private final List<Indicator> indicators;

    public MaxStrategy(int stochasticDepth, int stochasticEmaDepth, int bollingerDepth, int bollingerMultiplier) {
        this.indicators = List.of(
                new StochasticOscillator(stochasticDepth, stochasticEmaDepth),
                new BollingerBands(bollingerDepth, bollingerMultiplier)
        );
    }

    public MaxStrategy(int stochasticDepth, int stochasticEmaDepth, int stochasticSmooth, int bollingerDepth, int bollingerMultiplier) {
        this.indicators = List.of(
                new StochasticOscillator(stochasticDepth, stochasticEmaDepth, stochasticSmooth),
                new BollingerBands(bollingerDepth, bollingerMultiplier)
        );
    }

    @Override
    public void addCandle(CustomCandle candle) {
        indicators.forEach(i -> i.addCandle(candle));
    }

    @Override
    public List<Indicator> getIndicators() {
        return indicators;
    }

    @Override
    public boolean isTimeToBuy() {
        return false;
    }

    @Override
    public boolean isTimeToSell() {
        return false;
    }
}
