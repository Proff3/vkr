package ru.pronin.study.vkr.tradeBot.brokerAPI.tinkoff;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ru.pronin.study.vkr.tradeBot.brokerAPI.BrokerDAO;
import ru.pronin.study.vkr.tradeBot.brokerAPI.InstrumentsDataDAO;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomMarketInstrument;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomCandleResolution;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InstrumentsDataDAOTinkoffImplTest {
    @Autowired
    @Qualifier("brokerDAOTinkoffImpl")
    private BrokerDAO tinkoff;
    private final Logger LOGGER = Logger.getLogger(InstrumentsDataDAOTinkoffImplTest.class.toString());

    @Test
    public void testGetAllInstruments() {
        List<CustomMarketInstrument> instrumentList = tinkoff.getInstrumentDAO().getAllInstruments();
        assertNotNull(instrumentList.get(0));
    }

    @Test
    public void testGetCandlesForWeeks() {
        //apple figi - BBG000B9XRY4
        List<CustomCandle> candles = null;
        try {
            candles = tinkoff.getInstrumentDAO().getRequiredNumberOfCandles(false,"BBG000B9XRY4", 20, CustomCandleResolution.HOUR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert candles != null;
        assertTrue(candles.size() > 0);
    }

    @Test
    void getRequiredNumberOfCandles() {
        String figi = "BBG000B9XRY4";
        InstrumentsDataDAO tinkoffInstruments = tinkoff.getInstrumentDAO();
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
        List<CustomCandle> candles = tinkoff.getInstrumentDAO()
                .getCandlesFromDateTime(
                        false,
                        figi,
                        OffsetDateTime.now().minusYears(1),
                        CustomCandleResolution.HOUR);
        assertNotEquals(0, candles.size());
    }

    @Test
    void testUSASession() throws Exception {
        List<CustomCandle> customCandles = tinkoff.getInstrumentDAO().getCandlesFromDateTime(true, "BBG000B9XRY4", OffsetDateTime.now().minusDays(3), CustomCandleResolution.HOUR);
        assertNotEquals(0, customCandles.size());
        assertFalse(customCandles.stream().allMatch(CustomCandle::isUSASession));
    }

    @Test
    void testNotUSASession() throws Exception {
        List<CustomCandle> customCandles = tinkoff.getInstrumentDAO().getCandlesFromDateTime(false, "BBG000B9XRY4", OffsetDateTime.now().minusDays(3), CustomCandleResolution.HOUR);
        assertNotEquals(0, customCandles.size());
        assertTrue(customCandles.stream().noneMatch(CustomCandle::isUSASession));
    }
}