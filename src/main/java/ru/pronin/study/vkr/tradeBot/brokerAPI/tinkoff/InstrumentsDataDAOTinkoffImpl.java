package ru.pronin.study.vkr.tradeBot.brokerAPI.tinkoff;

import ru.pronin.study.vkr.tradeBot.brokerAPI.InstrumentsDataDAO;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomMarketInstrument;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.RequestCounter;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomCandleResolution;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomCurrency;
import ru.pronin.study.vkr.tradeBot.brokerAPI.exceptions.CandlesNotFoundException;
import ru.pronin.study.vkr.tradeBot.brokerAPI.exceptions.NoMarketInstrumentException;
import ru.tinkoff.invest.openapi.MarketContext;
import ru.tinkoff.invest.openapi.model.rest.Candle;
import ru.tinkoff.invest.openapi.model.rest.CandleResolution;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class InstrumentsDataDAOTinkoffImpl implements InstrumentsDataDAO {

    private final Logger LOGGER = Logger.getLogger(InstrumentsDataDAOTinkoffImpl.class.toString());
    private MarketContext MARKET;
    private final RequestCounter counter = new RequestCounter();
    List<CustomMarketInstrument> instruments;

    public void setMARKET(MarketContext MARKET) {
        this.MARKET = MARKET;
    }

    @Override
    public List<CustomMarketInstrument> getAllInstruments() {
        if (instruments == null) {
            try {
                List<ru.tinkoff.invest.openapi.model.rest.MarketInstrument> tinkoffInstruments = MARKET.getMarketStocks().get().getInstruments();
                instruments = tinkoffInstruments
                        .stream()
                        .map(InstrumentsDataDAOTinkoffImpl::tinkoffInstrumentToCustom)
                        .collect(Collectors.toList());
                return instruments;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return instruments;
    }

    @Override
    public List<CustomCandle> getRequiredNumberOfCandles(boolean USASession, String figi, int requiredNumberOfCandles, CustomCandleResolution customCandleResolution) throws Exception {
        int maxIntervalInDays = getRequiredIntervalInDays(customCandleResolution);
        int skippedDays = maxIntervalInDays;
        List<CustomCandle> candles = new ArrayList<>();
        while(requiredNumberOfCandles > candles.size()){
            candles.addAll(0, getCandlesForTimeInterval(
                                        USASession,
                                        OffsetDateTime.now().minusDays(skippedDays),
                                        OffsetDateTime.now().minusDays(skippedDays - maxIntervalInDays),
                                        figi,
                                        customCandleResolution));
            skippedDays += maxIntervalInDays;
        }
        return candles.stream().skip(candles.size() - requiredNumberOfCandles).collect(Collectors.toList());
    }

    @Override
    public List<CustomCandle> getCandlesFromDateTime(boolean USASession, String figi, OffsetDateTime startTime, CustomCandleResolution customCandleResolution) throws Exception {
        int maxIntervalInDays = getRequiredIntervalInDays(customCandleResolution);
        OffsetDateTime currentTime = startTime;
        List<CustomCandle> candles = new ArrayList<>();
        while(currentTime.isBefore(OffsetDateTime.now())){
            candles.addAll(0, getCandlesForTimeInterval(
                    USASession,
                    currentTime,
                    currentTime.plusDays(maxIntervalInDays),
                    figi,
                    customCandleResolution));
            LOGGER.info(String.valueOf(candles.size()));
            currentTime = currentTime.plusDays(maxIntervalInDays);
        }
        return candles;
    }

    private List<CustomCandle> getCandlesForTimeInterval(boolean USASession, OffsetDateTime dateFrom, OffsetDateTime dateTo, String figi, CustomCandleResolution resolution) throws Exception {
        CandleResolution candleResolution = CandleResolution.fromValue(resolution.getValue());
        checkCounter();
        return MARKET
                .getMarketCandles(figi, dateFrom, dateTo, candleResolution)
                .get()
                .orElseThrow(() -> {
                    try {
                        instruments
                                .stream()
                                .filter(instrument -> instrument.getFigi().equalsIgnoreCase(figi))
                                .findFirst()
                                .orElseThrow(NoMarketInstrumentException::new);
                        return new CandlesNotFoundException();
                    } catch (NoMarketInstrumentException e) {
                        return new NoMarketInstrumentException();
                    }
                })
                .getCandles()
                .stream()
                .filter(c -> !USASession || isUSASession(c))
                .map(InstrumentsDataDAOTinkoffImpl::tinkoffCandleToCustom)
                .collect(Collectors.toList());
    }

    private static CustomMarketInstrument tinkoffInstrumentToCustom(MarketInstrument tinkoffInstrument){
        CustomCurrency customCurrency = CustomCurrency.valueOf(tinkoffInstrument.getCurrency().toString());
        return new CustomMarketInstrument(
                tinkoffInstrument.getFigi(),
                tinkoffInstrument.getTicker(),
                tinkoffInstrument.getIsin(),
                customCurrency,
                tinkoffInstrument.getName()
        );
    }

    private static CustomCandle tinkoffCandleToCustom(Candle candle){
        CustomCandleResolution resolution = CustomCandleResolution.fromValue(candle.getInterval().getValue());
        return new CustomCandle(
                candle.getFigi(),
                resolution,
                candle.getO(),
                candle.getC(),
                candle.getH(),
                candle.getL(),
                candle.getV(),
                candle.getTime()
        );
    }


    /**
     * Tinkoff OpenApi has limits on number of candles, therefore we need to map resolution to number of days - max value in the limits
     * @param resolution
     * @return Max interval in days
     */
    private int getRequiredIntervalInDays(CustomCandleResolution resolution){
        switch (resolution){
            case _1MIN:
            case _2MIN:
            case _3MIN:
            case _5MIN:
            case _10MIN:
            case _15MIN:
            case _30MIN:
                return 1;
            case HOUR:
                return 7;
            case DAY:
                return 364;
            case WEEK:
                return 364 * 2;
            case MONTH:
                return 365 * 3;
        }
        return 1;
    }

    private void checkCounter() throws InterruptedException {
        while(counter.getValue() > 150) {
            Thread.sleep(10 * 1000);
            if (counter.getStartDate().plusMinutes(1).isAfter(OffsetDateTime.now())){
                counter.resetCounter();
                counter.setStartDate(OffsetDateTime.now());
            }
        }
    }

    private boolean isUSASession(Candle candle) {
        OffsetDateTime time = candle.getTime();
        int hours = time.getHour();
        int minutes = time.getMinute();
        return (hours >= 16 && minutes >= 30) && (hours < 22);
    }
}
