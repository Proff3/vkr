package ru.pronin.study.vkr.tradeBot;

import ru.pronin.study.vkr.tradeBot.brokerAPI.BrokerDAO;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomCandleResolution;
import ru.pronin.study.vkr.tradeBot.brokerAPI.tinkoff.*;
import ru.pronin.study.vkr.tradeBot.indicators.BollingerBands;
import ru.pronin.study.vkr.tradeBot.indicators.SMA;
import ru.pronin.study.vkr.tradeBot.indicators.StochasticOscillator;
import ru.pronin.study.vkr.tradeBot.strategies.manager.IndicatorManager;
import ru.pronin.study.vkr.tradeBot.strategies.manager.StochasticManager;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class TestMaxStrategy {
    public static void main(String[] args) throws Exception {
        List<String> figiStocks = List.of("BBG000B9XRY4", "BBG002B04MT8"); //APPLE, UBER
        StochasticOscillator so = new StochasticOscillator(14, 5, 2);
        IndicatorManager<StochasticOscillator> stochasticManager = new StochasticManager();
        BollingerBands bb = new BollingerBands(20, 2);
        SMA sma = new SMA(15);
        BrokerDAO broker = new BrokerDAOTinkoffImpl(
                true,
                new InstrumentsDataDAOTinkoffImpl(),
                new SubscriptionDAOTinkoffImpl(),
                new TradingDAOTinkoffImpl(),
                new PortfolioDAOTinkoffImpl());
        List<CustomCandle> candles = broker.getInstrumentsDataDAO().getRequiredNumberOfCandles(false, figiStocks.get(1), 500, CustomCandleResolution._5MIN);
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

    private static String getToken() {
        Properties prop = new Properties();
        FileReader fr;
        try {
            fr = new FileReader("./src/main/resources/application.properties");
            prop.load(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty("tinkoff.sandbox.token");
    }
}
