package ru.pronin.study.vkr.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.pronin.study.vkr.tradeBot.brokerAPI.BrokerDAO;
import ru.pronin.study.vkr.tradeBot.brokerAPI.tinkoff.BrokerDAOTinkoffImpl;

@Configuration
@ComponentScan(basePackages = "ru.pronin.study.vkr.tradeBot")
public class AppConfiguration {

    @Autowired
    BrokerDAOTinkoffImpl tinkoffBroker;

    @Bean("tinkoffBrokerDAO")
    BrokerDAO getTinkoffBroker() {
        return tinkoffBroker.init(true);
    }

//    @Bean("tinkoffBrokerDAOProm")
//    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    BrokerDAO getTinkoffBrokerProm() {
//        return tinkoffBroker.init(false);
//    }

}
