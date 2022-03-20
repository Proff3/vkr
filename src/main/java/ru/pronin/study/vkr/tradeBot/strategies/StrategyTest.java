package ru.pronin.study.vkr.tradeBot.strategies;

import ru.pronin.study.vkr.tradeBot.brokerAPI.BrokerDAO;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomMarketInstrument;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomCandleResolution;
import ru.pronin.study.vkr.tradeBot.indicators.Indicator;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrategyTest implements Runnable {
    final List<Indicator> strategies;
    final OffsetDateTime startDate;
    final CustomCandleResolution resolution;
    final BrokerDAO broker;

    public StrategyTest(List<Indicator> strategies, OffsetDateTime startDate, CustomCandleResolution resolution, BrokerDAO broker) {
        this.strategies = strategies;
        this.startDate = startDate;
        this.resolution = resolution;
        this.broker = broker;
    }

    @Override
    public void run() {
        Map<String, List<CustomCandle>> candlesMap = new HashMap<>();
        List<CustomMarketInstrument> instruments = broker.getInstrumentsDataDAO().getAllInstruments();
        instruments.forEach(instrument -> {
            String figi = instrument.getFigi();
            List<CustomCandle> middlewareCandles = new ArrayList<>();
            try {
                middlewareCandles =
                        broker.getInstrumentsDataDAO().getCandlesFromDateTime(false, figi, startDate, resolution);
            } catch (Exception e) {
                e.printStackTrace();
            }
            candlesMap.put(figi, middlewareCandles);
        });
        //strategies.forEach();
    }
}
