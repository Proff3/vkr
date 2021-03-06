package ru.pronin.study.vkr.tradeBot.brokerAPI.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CustomOperationType {
    BUY("Buy"),
    SELL("Sell");

    private final String value;

    CustomOperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static CustomOperationType fromValue(String text) {
        for (CustomOperationType b : CustomOperationType.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
