package ru.pronin.study.vkr.tradeBot.brokerAPI.entities;

import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomOperationType;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomOrderStatus;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomOrderType;

import java.math.BigDecimal;

public class CustomOrder extends AbstractOrder {

    private String figi = null;
    private BigDecimal price = null;

    public CustomOrder(String orderId,
                       CustomOperationType operation,
                       CustomOrderStatus status,
                       Integer requestedLots,
                       Integer executedLots,
                       CustomOrderType type,
                       String figi,
                       BigDecimal price) {
        super(orderId, operation, status, requestedLots, executedLots, type);
        this.figi = figi;
        this.price = price;
    }

    public String getFigi() {
        return figi;
    }

    public void setFigi(String figi) {
        this.figi = figi;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
