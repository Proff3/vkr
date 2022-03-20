package ru.pronin.study.vkr.tradeBot.brokerAPI.tinkoff;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ru.pronin.study.vkr.tradeBot.brokerAPI.BrokerDAO;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomPortfolioPosition;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomOperationType;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TradingDAOTinkoffImplTest {

    @Autowired
    @Qualifier("brokerDAOTinkoffImpl")
    @Spy
    private BrokerDAO tinkoff;

    Logger LOGGER = LoggerFactory.getLogger(TradingDAOTinkoffImplTest.class);

    @Test
    void placeLimitOrder() {
        int unexpected = 0;
        int expected = 0;
        //apple figi - BBG000B9XRY4
        try {
            unexpected = getLotsForFigiFromPortfolio(tinkoff, "BBG000B9XRY4");
            tinkoff.getTradingDAO().placeLimitOrder(
                    "BBG000B9XRY4",
                    1,
                    CustomOperationType.BUY,
                    BigDecimal.valueOf(144)
            );
            expected = getLotsForFigiFromPortfolio(tinkoff, "BBG000B9XRY4");
        } catch (ExecutionException | InterruptedException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        LOGGER.info("Unexpected: " + unexpected + "; expected: " + expected);
        assertNotEquals(unexpected, expected);
    }

    @Test
    void placeMarketOrder() {
        int unexpected = 0;
        int expected = 0;
        //apple figi - BBG000B9XRY4
        try {
            unexpected = getLotsForFigiFromPortfolio(tinkoff, "BBG000B9XRY4");
            tinkoff.getTradingDAO().placeMarketOrder(
                    "BBG000B9XRY4",
                    1,
                    CustomOperationType.BUY
            );
            expected = getLotsForFigiFromPortfolio(tinkoff, "BBG000B9XRY4");
        } catch (ExecutionException | InterruptedException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        LOGGER.info("Unexpected: " + unexpected + "; expected: " + expected);
        assertNotEquals(unexpected, expected);
    }

    @Test
    void getOrders() {
        int unexpected = 0;
        int expected = 0;
        //apple figi - BBG000B9XRY4
        try {
            unexpected = getLotsForFigiFromPortfolio(tinkoff, "BBG000B9XRY4");
            tinkoff.getTradingDAO().placeLimitOrder(
                    "BBG000B9XRY4",
                    1,
                    CustomOperationType.BUY,
                    BigDecimal.valueOf(144)
            );
            expected = getLotsForFigiFromPortfolio(tinkoff, "BBG000B9XRY4");
        } catch (ExecutionException | InterruptedException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        LOGGER.info("Unexpected: " + unexpected + "; expected: " + expected);
        assertNotEquals(unexpected, expected);
    }

    @Test
    void cancelOrder() throws ExecutionException, InterruptedException {
        String orderID = tinkoff.getTradingDAO().placeLimitOrder(
                "BBG000B9XRY4",
                1,
                CustomOperationType.BUY,
                BigDecimal.valueOf(144)
        ).getOrderId();
        //orders complete immediately cause it`s a sandbox mode
        //therefore orders cannot be cancelled
        tinkoff.getTradingDAO().cancelOrder(orderID);
        assertDoesNotThrow(() -> tinkoff.getTradingDAO().cancelOrder(orderID));
    }

    private int getLotsForFigiFromPortfolio(BrokerDAO broker, String figi) throws ExecutionException, InterruptedException {
         Optional<CustomPortfolioPosition> optional = broker.getPortfolioDAO().getPortfolio().getPositions()
                .stream()
                .filter(position -> position.getFigi().equalsIgnoreCase(figi))
                .findFirst();
         return optional.orElse(new CustomPortfolioPosition())
                .getLots();
    }
}