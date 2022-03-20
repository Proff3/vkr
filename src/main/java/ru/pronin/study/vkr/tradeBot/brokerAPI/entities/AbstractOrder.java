package ru.pronin.study.vkr.tradeBot.brokerAPI.entities;

import org.springframework.stereotype.Repository;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomOperationType;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomOrderStatus;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomOrderType;

public class AbstractOrder {

    private String orderId = null;
    private CustomOperationType operation = null;
    private CustomOrderStatus status = null;
    private Integer requestedLots = null;
    private Integer executedLots = null;
    private CustomOrderType type = null;

    public AbstractOrder(String orderId, CustomOperationType operation, CustomOrderStatus status, Integer requestedLots, Integer executedLots, CustomOrderType type) {
        this.orderId = orderId;
        this.operation = operation;
        this.status = status;
        this.requestedLots = requestedLots;
        this.executedLots = executedLots;
        this.type = type;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public CustomOperationType getOperation() {
        return operation;
    }

    public void setOperation(CustomOperationType operation) {
        this.operation = operation;
    }

    public CustomOrderStatus getStatus() {
        return status;
    }

    public void setStatus(CustomOrderStatus status) {
        this.status = status;
    }

    public Integer getRequestedLots() {
        return requestedLots;
    }

    public void setRequestedLots(Integer requestedLots) {
        this.requestedLots = requestedLots;
    }

    public Integer getExecutedLots() {
        return executedLots;
    }

    public void setExecutedLots(Integer executedLots) {
        this.executedLots = executedLots;
    }

    public CustomOrderType getType() {
        return type;
    }

    public void setType(CustomOrderType type) {
        this.type = type;
    }
}
