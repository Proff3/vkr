package ru.pronin.study.vkr.tradeBot.brokerAPI.tinkoff;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Subscriber;
import ru.pronin.study.vkr.tradeBot.brokerAPI.BrokerDAO;
import ru.pronin.study.vkr.tradeBot.brokerAPI.exceptions.StreamInitializationException;
import ru.tinkoff.invest.openapi.StreamingContext;
import ru.tinkoff.invest.openapi.model.streaming.StreamingEvent;
import ru.tinkoff.invest.openapi.model.streaming.StreamingRequest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionDAOTinkoffImplTest {

    @Spy
    private SubscriptionDAOTinkoffImpl tinkoffSubscription = new SubscriptionDAOTinkoffImpl();

    @Test
    void setSTREAM() {
        BrokerDAO tinkoff = new BrokerDAOTinkoffImpl(
                true,
                new InstrumentsDataDAOTinkoffImpl(),
                tinkoffSubscription,
                new TradingDAOTinkoffImpl(),
                new PortfolioDAOTinkoffImpl());
        assertThrows(StreamInitializationException.class, () -> tinkoffSubscription.setSTREAM(new StreamingContext() {
            @Override
            public void sendRequest(@NotNull StreamingRequest request) {
            }
            @Override
            public void subscribe(Subscriber<? super StreamingEvent> s) {
            }
        }));
    }
}