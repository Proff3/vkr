package ru.pronin.study.vkr.tradeBot.brokerAPI.enums;

public enum CustomCandleResolution {
    _1MIN("1min"),
    _2MIN("2min"),
    _3MIN("3min"),
    _5MIN("5min"),
    _10MIN("10min"),
    _15MIN("15min"),
    _30MIN("30min"),
    HOUR("hour"),
    DAY("day"),
    WEEK("week"),
    MONTH("month");

    private final String value;

    CustomCandleResolution(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static CustomCandleResolution getResolution(String text) {
        for (CustomCandleResolution b : CustomCandleResolution.values()) {
            if (String.valueOf(b.value).equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
