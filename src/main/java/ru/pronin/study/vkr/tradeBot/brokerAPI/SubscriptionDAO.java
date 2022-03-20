package ru.pronin.study.vkr.tradeBot.brokerAPI;

import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomCandleResolution;
import ru.pronin.study.vkr.tradeBot.brokerAPI.exceptions.EventHandlerNotInitializeException;

import java.util.function.Function;

public interface SubscriptionDAO {
    void subscribeOnCandles(String figi, CustomCandleResolution customCandleResolution) throws EventHandlerNotInitializeException;
    void subscribeOnInstrumentInfo(String figi) throws EventHandlerNotInitializeException;
    void subscribeOnOrderBook(String figi, int depth) throws EventHandlerNotInitializeException;
    /**
     * @param eventHandler - function, that applies the stream event and returns boolean means end of subscription
     */
    <SE> void setSubscriber(Function<SE, Boolean> eventHandler, Runnable setterOver);
}
