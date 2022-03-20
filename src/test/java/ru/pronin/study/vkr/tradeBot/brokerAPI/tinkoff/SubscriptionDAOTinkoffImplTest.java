package ru.pronin.study.vkr.tradeBot.brokerAPI.tinkoff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ru.pronin.study.vkr.tradeBot.brokerAPI.BrokerDAO;

@SpringBootTest
class SubscriptionDAOTinkoffImplTest {

    @Autowired
    @Qualifier("brokerDAOTinkoffImpl")
    private BrokerDAO tinkoff;
}