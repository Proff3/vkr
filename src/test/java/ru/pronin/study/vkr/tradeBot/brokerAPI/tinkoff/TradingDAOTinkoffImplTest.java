package ru.pronin.study.vkr.tradeBot.brokerAPI.tinkoff;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pronin.study.vkr.tradeBot.brokerAPI.BrokerDAO;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomOrder;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomPortfolioPosition;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomOperationType;
import ru.pronin.study.vkr.tradeBot.brokerAPI.exceptions.OrdersContextInitializationException;
import ru.tinkoff.invest.openapi.OrdersContext;
import ru.tinkoff.invest.openapi.model.rest.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TradingDAOTinkoffImplTest {

    Logger LOGGER = LoggerFactory.getLogger(TradingDAOTinkoffImplTest.class);
    @Spy
    private BrokerDAO tinkoff = new BrokerDAOTinkoffImpl(
            true,
            new InstrumentsDataDAOTinkoffImpl(),
            new SubscriptionDAOTinkoffImpl(),
            new TradingDAOTinkoffImpl(),
            new PortfolioDAOTinkoffImpl());;

    @Test
    void setORDERS() {
        TradingDAOTinkoffImpl tradingDAO = new TradingDAOTinkoffImpl();
        tinkoff = new BrokerDAOTinkoffImpl(
                true,
                new InstrumentsDataDAOTinkoffImpl(),
                new SubscriptionDAOTinkoffImpl(),
                tradingDAO,
                new PortfolioDAOTinkoffImpl());

        assertThrows(OrdersContextInitializationException.class, () -> tradingDAO.setORDERS(new OrdersContext() {
            @NotNull
            @Override
            public CompletableFuture<List<Order>> getOrders(@Nullable String brokerAccountId) {
                return null;
            }

            @NotNull
            @Override
            public CompletableFuture<PlacedLimitOrder> placeLimitOrder(@NotNull String figi, @NotNull LimitOrderRequest limitOrder, @Nullable String brokerAccountId) {
                return null;
            }

            @NotNull
            @Override
            public CompletableFuture<PlacedMarketOrder> placeMarketOrder(@NotNull String figi, @NotNull MarketOrderRequest marketOrder, @Nullable String brokerAccountId) {
                return null;
            }

            @NotNull
            @Override
            public CompletableFuture<Void> cancelOrder(@NotNull String orderId, @Nullable String brokerAccountId) {
                return null;
            }

            @NotNull
            @Override
            public String getPath() {
                return null;
            }
        }));
    }

    @Test
    void placeLimitOrder() {
        int unexpected = 0;
        int expected = 0;
        tinkoff = new BrokerDAOTinkoffImpl(
                true,
                new InstrumentsDataDAOTinkoffImpl(),
                new SubscriptionDAOTinkoffImpl(),
                new TradingDAOTinkoffImpl(),
                new PortfolioDAOTinkoffImpl());
        List<CustomOrder> orders = null;
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
        tinkoff = new BrokerDAOTinkoffImpl(
                true,
                new InstrumentsDataDAOTinkoffImpl(),
                new SubscriptionDAOTinkoffImpl(),
                new TradingDAOTinkoffImpl(),
                new PortfolioDAOTinkoffImpl());
        List<CustomOrder> orders = null;
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
        tinkoff = new BrokerDAOTinkoffImpl(
                true,
                new InstrumentsDataDAOTinkoffImpl(),
                new SubscriptionDAOTinkoffImpl(),
                new TradingDAOTinkoffImpl(),
                new PortfolioDAOTinkoffImpl());
        List<CustomOrder> orders = null;
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