package ru.pronin.study.vkr.tradeBot.brokerAPI;

import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomMarketInstrument;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomCandleResolution;

import java.time.OffsetDateTime;
import java.util.List;

public interface InstrumentsDataDAO {
    List<CustomMarketInstrument> getAllInstruments();
    List<CustomCandle> getRequiredNumberOfCandles(boolean USASession, String figi, int numberOfCandles, CustomCandleResolution customCandleResolution) throws Exception;
    List<CustomCandle> getCandlesFromDateTime(boolean USASession, String figi, OffsetDateTime startTime, CustomCandleResolution customCandleResolution) throws Exception;

}
