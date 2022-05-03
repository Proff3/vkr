package ru.pronin.study.vkr.tradeBot.indicators;

import org.junit.jupiter.api.Test;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomCandle;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BollingerBandsTest {

    BollingerBands bb = new BollingerBands(10, 2);
    long i = 0;

    Function<BigDecimal, CustomCandle> createCandle = (value) -> {
        CustomCandle candle = new CustomCandle();
        candle.setC(value);
        candle.setH(value.add(BigDecimal.TEN));
        candle.setL(value.subtract(BigDecimal.TEN));
        candle.setTime(OffsetDateTime.now().plusSeconds(i++));
        return candle;
    };

    @Test
    void addCandle() throws Exception {
        bb.addCandle(createCandle.apply(BigDecimal.valueOf(10L)));
        bb.addCandle(createCandle.apply(BigDecimal.valueOf(15L)));
        bb.addCandle(createCandle.apply(BigDecimal.valueOf(13L)));
        bb.addCandle(createCandle.apply(BigDecimal.valueOf(16L)));
        bb.addCandle(createCandle.apply(BigDecimal.valueOf(15L)));
        bb.addCandle(createCandle.apply(BigDecimal.valueOf(12L)));
        bb.addCandle(createCandle.apply(BigDecimal.valueOf(16L)));
        bb.addCandle(createCandle.apply(BigDecimal.valueOf(17L)));
        bb.addCandle(createCandle.apply(BigDecimal.valueOf(18L)));
        bb.addCandle(createCandle.apply(BigDecimal.valueOf(25L)));
        assertEquals(bb.getHLValue().setScale(1, RoundingMode.HALF_UP), BigDecimal.valueOf(23.4));
        assertEquals(bb.getLLValue().setScale(1, RoundingMode.HALF_UP), BigDecimal.valueOf(8.0));
        assertEquals(bb.getMLValue().setScale(1, RoundingMode.HALF_UP), BigDecimal.valueOf(15.7));
    }
}