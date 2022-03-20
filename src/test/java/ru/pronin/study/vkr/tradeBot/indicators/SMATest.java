package ru.pronin.study.vkr.tradeBot.indicators;

import org.junit.jupiter.api.Test;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomCandle;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class SMATest {

    SMA sma = new SMA(5);
    int i = 0;

    Function<BigDecimal, CustomCandle> createCandle = closeValue -> {
        CustomCandle candle = new CustomCandle();
        candle.setC(closeValue);
        candle.setTime(OffsetDateTime.now().plusMinutes(i++));
        return candle;
    };

    @Test
    void addCandle() {
        sma.addCandle(createCandle.apply(new BigDecimal(10)));
        sma.addCandle(createCandle.apply(new BigDecimal(20)));
        sma.addCandle(createCandle.apply(new BigDecimal(30)));
        sma.addCandle(createCandle.apply(new BigDecimal(40)));
        sma.addCandle(createCandle.apply(new BigDecimal(50)));
        assertEquals(new BigDecimal(30).setScale(4, RoundingMode.HALF_UP), sma.getCurrentValue().getValue());
    }
}