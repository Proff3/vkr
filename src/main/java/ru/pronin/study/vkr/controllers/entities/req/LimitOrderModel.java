package ru.pronin.study.vkr.controllers.entities.req;

import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomOperationType;

import java.math.BigDecimal;

public class LimitOrderModel {

    private CustomOperationType operationType;
    private BigDecimal activationPrice;
    private String figi;
    private int lots;

    public LimitOrderModel() {
    }

    public CustomOperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(CustomOperationType operationType) {
        this.operationType = operationType;
    }

    public BigDecimal getActivationPrice() {
        return activationPrice;
    }

    public void setActivationPrice(BigDecimal activationPrice) {
        this.activationPrice = activationPrice;
    }

    public String getFigi() {
        return figi;
    }

    public void setFigi(String figi) {
        this.figi = figi;
    }

    public int getLots() {
        return lots;
    }

    public void setLots(int lots) {
        this.lots = lots;
    }
}
