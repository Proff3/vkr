package ru.pronin.study.vkr.tradeBot.brokerAPI.tinkoff;

import ru.pronin.study.vkr.tradeBot.brokerAPI.SubscriptionDAO;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomCandleResolution;
import ru.pronin.study.vkr.tradeBot.brokerAPI.exceptions.EventHandlerNotInitializeException;
import ru.pronin.study.vkr.tradeBot.brokerAPI.exceptions.StreamInitializationException;
import ru.tinkoff.invest.openapi.StreamingContext;
import ru.tinkoff.invest.openapi.model.streaming.CandleInterval;
import ru.tinkoff.invest.openapi.model.streaming.StreamingEvent;
import ru.tinkoff.invest.openapi.model.streaming.StreamingRequest;

import java.util.function.Function;

public class SubscriptionDAOTinkoffImpl implements SubscriptionDAO {

    private Boolean isSubscribeSet;
    private StreamingContext STREAM;

    public void setSTREAM(StreamingContext STREAM) throws StreamInitializationException {
        if (this.STREAM != null) throw new StreamInitializationException();
        this.STREAM = STREAM;
    }

    @Override
    public void subscribeOnCandles(String figi, CustomCandleResolution customCandleResolution)
            throws EventHandlerNotInitializeException {
        if (!isSubscribeSet) throw new EventHandlerNotInitializeException();
        CandleInterval interval = CandleInterval.fromValue(customCandleResolution.getValue());
        StreamingRequest candleRequest = StreamingRequest.subscribeCandle(figi, interval);
        STREAM.sendRequest(candleRequest);
    }

    @Override
    public void subscribeOnInstrumentInfo(String figi) throws EventHandlerNotInitializeException {
        if (isSubscribeSet) throw new EventHandlerNotInitializeException();
        StreamingRequest instrumentInfoRequest = StreamingRequest.subscribeInstrumentInfo(figi);
        STREAM.sendRequest(instrumentInfoRequest);
    }

    @Override
    public void subscribeOnOrderBook(String figi, int depth) throws EventHandlerNotInitializeException {
        if (isSubscribeSet) throw new EventHandlerNotInitializeException();
        StreamingRequest OrderBookRequest = StreamingRequest.subscribeOrderbook(figi, depth);
        STREAM.sendRequest(OrderBookRequest);
    }

    @Override
    public <SE> void setSubscriber(Function<SE, Boolean> eventHandler, Runnable setterOver) {
        TinkoffSubscriber<StreamingEvent> subscriber = new TinkoffSubscriber(eventHandler, setterOver);
        System.out.println("Subscribed");
        STREAM.subscribe(subscriber);
        isSubscribeSet = true;
    }
}
