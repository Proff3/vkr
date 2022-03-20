package ru.pronin.study.vkr.tradeBot.brokerAPI.tinkoff;

import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ru.pronin.study.vkr.ConfigurationProp;
import ru.pronin.study.vkr.tradeBot.brokerAPI.BrokerDAO;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomPortfolioPosition;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomOperationType;
import ru.pronin.study.vkr.tradeBot.brokerAPI.exceptions.PortfolioInitializationException;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PortfolioDAOTinkoffImplTest {

    @Autowired
    @Qualifier("brokerDAOTinkoffImpl")
    @Spy
    private BrokerDAO tinkoff;

    Logger LOGGER = LoggerFactory.getLogger(PortfolioDAOTinkoffImplTest.class);

    Function<String, Integer> countPositionsForFigi = (figi) -> tinkoff
            .getPortfolioDAO()
            .getPortfolio()
            .getPositions()
            .stream()
            .filter(pos -> pos.getFigi().equalsIgnoreCase(figi))
            .mapToInt(CustomPortfolioPosition::getLots)
            .sum();

    @Test
    void getPortfolio() throws ExecutionException, InterruptedException {
        int beforePlacingOrder = countPositionsForFigi.apply("BBG000B9XRY4");
        int lots = 10;
        //orders complete immediately cause it`s a sandbox mode
        tinkoff.getTradingDAO().placeLimitOrder(
                "BBG000B9XRY4",
                lots,
                CustomOperationType.BUY,
                BigDecimal.valueOf(144)
        );
        int afterPlacingOrder = countPositionsForFigi.apply("BBG000B9XRY4");
        assertEquals(lots, afterPlacingOrder - beforePlacingOrder);
    }

    @Test
    void setPORTFOLIO() {
        assertThrows(PortfolioInitializationException.class,
                () -> {
            PortfolioDAOTinkoffImpl portfolio = (PortfolioDAOTinkoffImpl)tinkoff.getPortfolioDAO();
            portfolio.setPORTFOLIO(null);
        });
    }
}
