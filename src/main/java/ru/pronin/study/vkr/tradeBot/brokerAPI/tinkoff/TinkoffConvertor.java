package ru.pronin.study.vkr.tradeBot.brokerAPI.tinkoff;

import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomCandleResolution;
import ru.tinkoff.invest.openapi.model.rest.Candle;
import ru.tinkoff.invest.openapi.model.streaming.StreamingEvent;

public class TinkoffConvertor {
    static public CustomCandle convertOriginCandleToCustom(Candle candle){
        return new CustomCandle(
                candle.getFigi(),
                CustomCandleResolution.getResolution(candle.getInterval().getValue()),
                candle.getO(),
                candle.getC(),
                candle.getH(),
                candle.getL(),
                candle.getV(),
                candle.getTime()
        );
    }
    static public CustomCandle convertStreamingEventCandleToCustom(StreamingEvent.Candle candle){
        return new CustomCandle(
                candle.getFigi(),
                CustomCandleResolution.getResolution(candle.getInterval().getValue()),
                candle.getOpenPrice(),
                candle.getClosingPrice(),
                candle.getHighestPrice(),
                candle.getLowestPrice(),
                candle.getTradingValue(),
                candle.getDateTime()
        );
    }
}
