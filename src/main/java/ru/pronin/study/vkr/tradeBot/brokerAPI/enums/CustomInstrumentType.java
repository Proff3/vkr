package ru.pronin.study.vkr.tradeBot.brokerAPI.enums;

public enum CustomInstrumentType {
    STOCK("Stock"),
    CURRENCY("Currency"),
    BOND("Bond"),
    ETF("Etf");

    private final String value;

    CustomInstrumentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static CustomInstrumentType fromValue(String text) {
        for (CustomInstrumentType b : CustomInstrumentType.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
