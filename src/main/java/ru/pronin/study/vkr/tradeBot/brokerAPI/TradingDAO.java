package ru.pronin.study.vkr.tradeBot.brokerAPI;

import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomOrder;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomPlacedOrder;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomOperationType;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface TradingDAO {
    CustomPlacedOrder placeLimitOrder(String figi, int lots, CustomOperationType operation, BigDecimal price) throws ExecutionException, InterruptedException;
    CustomPlacedOrder placeMarketOrder(String figi, int lots, CustomOperationType operation) throws ExecutionException, InterruptedException;
    List<CustomOrder> getOrders() throws ExecutionException, InterruptedException;
    void cancelOrder(String orderId);
}
