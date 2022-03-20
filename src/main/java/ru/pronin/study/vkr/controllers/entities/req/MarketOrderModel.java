package ru.pronin.study.vkr.controllers.entities.req;

import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomOperationType;

public class MarketOrderModel {

    private CustomOperationType operationType;
    private String figi;
    private int lots;

    public MarketOrderModel() {
    }

    public CustomOperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(CustomOperationType operationType) {
        this.operationType = operationType;
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
