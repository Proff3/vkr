package ru.pronin.study.vkr.tradeBot.brokerAPI.tinkoff;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.pronin.study.vkr.tradeBot.brokerAPI.BrokerDAO;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomMarketInstrument;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomCandleResolution;
import ru.tinkoff.invest.openapi.MarketContext;
import ru.tinkoff.invest.openapi.model.rest.Candle;
import ru.tinkoff.invest.openapi.model.rest.CandleResolution;
import ru.tinkoff.invest.openapi.model.rest.Candles;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InstrumentsDataDAOTinkoffImplTest {

    private BrokerDAO tinkoff;
    private final Logger LOGGER = Logger.getLogger(InstrumentsDataDAOTinkoffImplTest.class.toString());

    Function<Integer, Candle> createCandle = (hour) -> {
        OffsetDateTime time = OffsetDateTime.of(2022, 3, 17, hour, 30, 0, 0, ZoneOffset.ofHours(3));
        Candle candle = new Candle();
        candle.setTime(time);
        candle.setC(BigDecimal.ZERO);
        candle.setO(BigDecimal.ZERO);
        candle.setL(BigDecimal.ZERO);
        candle.setH(BigDecimal.ZERO);
        candle.setV(0);
        candle.setFigi("");
        candle.setInterval(CandleResolution._1MIN);
        return candle;
    };

    @Spy
    private InstrumentsDataDAOTinkoffImpl tinkoffInstruments = new InstrumentsDataDAOTinkoffImpl();

    @BeforeEach
    void init(){
        tinkoff = new BrokerDAOTinkoffImpl(
                true,
                tinkoffInstruments,
                new SubscriptionDAOTinkoffImpl(),
                new TradingDAOTinkoffImpl(),
                new PortfolioDAOTinkoffImpl());
    }

    @Test
    public void testGetAllInstruments() {
        List<CustomMarketInstrument> instrumentList = tinkoffInstruments.getAllInstruments();
        assertNotNull(instrumentList.get(0));
    }

    @Test
    public void testGetCandlesForWeeks() {
        //apple figi - BBG000B9XRY4
        List<CustomCandle> candles = null;
        try {
            candles = tinkoffInstruments.getRequiredNumberOfCandles(false,"BBG000B9XRY4", 20, CustomCandleResolution.HOUR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert candles != null;
        assertTrue(candles.size() > 0);
    }

    @Test
    public void testSetMARKET() {
        verify(tinkoffInstruments, times(1)).setMARKET(any());
    }

    @Test
    void getRequiredNumberOfCandles() {
        String figi = "BBG000B9XRY4";
        assertAll(
                () -> {
                    List<CustomCandle> candles = tinkoffInstruments
                            .getRequiredNumberOfCandles(false, figi, 10, CustomCandleResolution._1MIN);
                    assertNotEquals(0, candles.size());
                },
                () -> {
                    List<CustomCandle> candles = tinkoffInstruments
                            .getRequiredNumberOfCandles(false, figi, 10, CustomCandleResolution._30MIN);
                    assertNotEquals(0, candles.size());
                },
                () -> {
                    List<CustomCandle> candles = tinkoffInstruments
                            .getRequiredNumberOfCandles(false, figi, 10, CustomCandleResolution.HOUR);
                    assertNotEquals(0, candles.size());
                },
                () -> {
                    List<CustomCandle> candles = tinkoffInstruments
                            .getRequiredNumberOfCandles(false, figi, 10, CustomCandleResolution.DAY);
                    assertNotEquals(0, candles.size());
                },
                () -> {
                    List<CustomCandle> candles = tinkoffInstruments
                            .getRequiredNumberOfCandles(false, figi, 10, CustomCandleResolution.WEEK);
                    assertNotEquals(0, candles.size());
                },
                () -> {
                    List<CustomCandle> candles = tinkoffInstruments
                            .getRequiredNumberOfCandles(false, figi, 10, CustomCandleResolution.MONTH);
                    assertNotEquals(0, candles.size());
                }
        );
    }

    @Test
    void getCandlesFromDateTime() throws Exception {
        String figi = "BBG000B9XRY4";
        List<CustomCandle> candles = tinkoffInstruments
                .getCandlesFromDateTime(
                        false,
                        figi,
                        OffsetDateTime.now().minusYears(1),
                        CustomCandleResolution.HOUR);
        assertNotEquals(0, candles.size());
    }

    @Test
    void testUSASession() throws Exception {
        Candles candles = new Candles();
        candles.setCandles(List.of(createCandle.apply(17)));
        MarketContext marketContext = Mockito.mock(MarketContext.class);
        when(marketContext.getMarketCandles(any(), any(), any(), any())).thenReturn(CompletableFuture.completedFuture(Optional.of(candles)));
        InstrumentsDataDAOTinkoffImpl is = new InstrumentsDataDAOTinkoffImpl();
        is.setMARKET(marketContext);
        List<CustomCandle> customCandles = is.getCandlesFromDateTime(true, "", OffsetDateTime.now(), CustomCandleResolution._5MIN);
        assertEquals(1, customCandles.size());
    }

    @Test
    void testNotUSASession() throws Exception {
        Candles candles = new Candles();
        candles.setCandles(List.of(createCandle.apply(23)));
        MarketContext marketContext = Mockito.mock(MarketContext.class);
        when(marketContext.getMarketCandles(any(), any(), any(), any())).thenReturn(CompletableFuture.completedFuture(Optional.of(candles)));
        InstrumentsDataDAOTinkoffImpl is = new InstrumentsDataDAOTinkoffImpl();
        is.setMARKET(marketContext);
        List<CustomCandle> customCandles = is.getCandlesFromDateTime(true, "", OffsetDateTime.now(), CustomCandleResolution._5MIN);
        assertEquals(0, customCandles.size());
    }
}