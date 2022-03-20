package ru.pronin.study.vkr.tradeBot.brokerAPI.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CustomOrderType {
    LIMIT("Limit"),
    MARKET("Market");

    private final String value;

    CustomOrderType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static CustomOrderType fromValue(String text) {
        for (CustomOrderType b : CustomOrderType.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
