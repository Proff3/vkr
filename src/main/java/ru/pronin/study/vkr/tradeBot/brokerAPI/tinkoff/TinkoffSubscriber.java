package ru.pronin.study.vkr.tradeBot.brokerAPI.tinkoff;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import ru.tinkoff.invest.openapi.model.streaming.StreamingEvent;

import java.util.function.Function;
import java.util.logging.Logger;

public class TinkoffSubscriber<SE extends StreamingEvent> implements Subscriber<SE> {

    private final Logger LOGGER = Logger.getLogger(String.valueOf(TinkoffSubscriber.class));
    private Subscription subscription;
    private final Function<SE, Boolean> eventHandler;
    private final Runnable setOverHandle;

    public TinkoffSubscriber(Function<SE, Boolean> eventHandler, Runnable setOverHandler) {
        this.eventHandler = eventHandler;
        this.setOverHandle = setOverHandler;
    }

    @Override
    public void onSubscribe(Subscription s) {
        this.subscription = s;
        subscription.request(10);
    }

    @Override
    public void onNext(SE se) {
        Boolean isEnded = eventHandler.apply(se);
        int n = isEnded ? 0 : 100;
        subscription.request(n);
    }

    @Override
    public void onError(Throwable t) {
        LOGGER.info(t.getMessage());
        setOverHandle.run();
    }

    @Override
    public void onComplete() {
        LOGGER.info("Completed");
        setOverHandle.run();
    }
}
