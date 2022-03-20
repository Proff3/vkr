package ru.pronin.study.vkr.tradeBot.brokerAPI.tinkoff;

import org.springframework.stereotype.Service;
import ru.pronin.study.vkr.tradeBot.brokerAPI.TradingDAO;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomMoneyAmount;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomOrder;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomPlacedOrder;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomCurrency;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomOperationType;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomOrderStatus;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomOrderType;
import ru.pronin.study.vkr.tradeBot.brokerAPI.exceptions.OrdersContextInitializationException;
import ru.tinkoff.invest.openapi.OrdersContext;
import ru.tinkoff.invest.openapi.model.rest.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class TradingDAOTinkoffImpl implements TradingDAO {

    private OrdersContext ORDERS;
    private String brokerAccountID;

    public void setORDERS(OrdersContext ORDERS) throws OrdersContextInitializationException {
        if(this.ORDERS != null) throw new OrdersContextInitializationException();
        this.ORDERS = ORDERS;
    }

    public void setBrokerAccountID(String brokerAccountID) {
        this.brokerAccountID = brokerAccountID;
    }

    @Override
    public CustomPlacedOrder placeLimitOrder(String figi, int lots, CustomOperationType operation, BigDecimal price) throws ExecutionException, InterruptedException {
        LimitOrderRequest orderRequest = new LimitOrderRequest();
        orderRequest.setLots(lots);
        OperationType operationType = OperationType.fromValue(operation.getValue());
        orderRequest.setOperation(operationType);
        orderRequest.setPrice(price);
        PlacedLimitOrder placedOrder = ORDERS.placeLimitOrder(figi, orderRequest, brokerAccountID).join();
        CustomCurrency customCurrency;
        BigDecimal commission;
        if (placedOrder.getCommission() == null) {
            customCurrency = CustomCurrency.USD;
            commission = BigDecimal.ZERO;
        } else {
            customCurrency = CustomCurrency.fromValue(placedOrder.getCommission().getCurrency().toString());
            commission = placedOrder.getCommission().getValue();
        }
        return new CustomPlacedOrder(
                placedOrder.getOrderId(),
                CustomOperationType.fromValue(placedOrder.getOperation().getValue()),
                CustomOrderStatus.fromValue(placedOrder.getStatus().getValue()),
                placedOrder.getRejectReason(),
                placedOrder.getMessage(),
                placedOrder.getRequestedLots(),
                placedOrder.getExecutedLots(),
                new CustomMoneyAmount(customCurrency, commission),
                CustomOrderType.LIMIT
        );
    }

    @Override
    public CustomPlacedOrder placeMarketOrder(String figi, int lots, CustomOperationType operation) throws ExecutionException, InterruptedException {
        MarketOrderRequest orderRequest = new MarketOrderRequest();
        orderRequest.setLots(lots);
        OperationType operationType = OperationType.fromValue(operation.getValue());
        orderRequest.setOperation(operationType);
        PlacedMarketOrder placedOrder = ORDERS.placeMarketOrder(figi, orderRequest, brokerAccountID).get();
        CustomCurrency customCurrency;
        BigDecimal commission;
        if (placedOrder.getCommission() == null) {
            customCurrency = CustomCurrency.USD;
            commission = BigDecimal.ZERO;
        } else {
            customCurrency = CustomCurrency.fromValue(placedOrder.getCommission().getCurrency().toString());
            commission = placedOrder.getCommission().getValue();
        }
        return new CustomPlacedOrder(
                placedOrder.getOrderId(),
                CustomOperationType.fromValue(placedOrder.getOperation().getValue()),
                CustomOrderStatus.fromValue(placedOrder.getStatus().getValue()),
                placedOrder.getRejectReason(),
                placedOrder.getMessage(),
                placedOrder.getRequestedLots(),
                placedOrder.getExecutedLots(),
                new CustomMoneyAmount(customCurrency, commission),
                CustomOrderType.MARKET
        );
    }

    @Override
    public List<CustomOrder> getOrders() {
        List<Order> orders = ORDERS.getOrders(brokerAccountID).join();
        System.out.println("orders " + orders);
        return orders.stream().map(tinkoffOrder -> new CustomOrder(
                    tinkoffOrder.getOrderId(),
                    CustomOperationType.fromValue(tinkoffOrder.getOperation().getValue()),
                    CustomOrderStatus.fromValue(tinkoffOrder.getStatus().getValue()),
                    tinkoffOrder.getRequestedLots(),
                    tinkoffOrder.getExecutedLots(),
                    CustomOrderType.fromValue(tinkoffOrder.getType().getValue()),
                    tinkoffOrder.getFigi(),
                    tinkoffOrder.getPrice()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void cancelOrder(String orderId) {
        ORDERS.cancelOrder(orderId, brokerAccountID);
    }
}
