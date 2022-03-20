package ru.pronin.study.vkr.tradeBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pronin.study.vkr.tradeBot.brokerAPI.BrokerDAO;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomCandleResolution;
import ru.pronin.study.vkr.tradeBot.indicators.BollingerBands;
import ru.pronin.study.vkr.tradeBot.indicators.SMA;
import ru.pronin.study.vkr.tradeBot.indicators.StochasticOscillator;
import ru.pronin.study.vkr.tradeBot.strategies.manager.IndicatorManager;
import ru.pronin.study.vkr.tradeBot.strategies.manager.StochasticManager;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestMaxStrategy {

    @Autowired
    @Qualifier("tinkoffBrokerDAO")
    BrokerDAO broker;

    @GetMapping("/test")
    public void test() throws Exception {
        List<String> figiStocks = List.of("BBG000B9XRY4", "BBG002B04MT8"); //APPLE, UBER
        StochasticOscillator so = new StochasticOscillator(14, 5, 2);
        IndicatorManager<StochasticOscillator> stochasticManager = new StochasticManager();
        BollingerBands bb = new BollingerBands(20, 2);
        SMA sma = new SMA(15);
        List<CustomCandle> candles = broker.getInstrumentDAO().getRequiredNumberOfCandles(false, figiStocks.get(1), 500, CustomCandleResolution._5MIN);
        candles.forEach(c -> {
            so.addCandle(c);
            bb.addCandle(c);
            stochasticManager.processIndicatorValues(so);
            if(stochasticManager.isTimeToBuy(so, c) || stochasticManager.isTimeToSell(so, c)) {
                System.out.println("Is time to buy: " + stochasticManager.isTimeToBuy(so, c));
                System.out.println("Is time to sell: " + stochasticManager.isTimeToSell(so, c));
                System.out.println("Time is: " + c.getTime());
            }
        });
    }
}
