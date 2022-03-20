package ru.pronin.study.vkr.tradeBot.brokerAPI.entities;

import java.time.OffsetDateTime;

public class RequestCounter {
    private int value;
    private OffsetDateTime startDate;

    public void resetCounter(){
        value = 0;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }
}
