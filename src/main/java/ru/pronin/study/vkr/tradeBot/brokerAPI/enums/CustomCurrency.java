package ru.pronin.study.vkr.tradeBot.brokerAPI.enums;

public enum CustomCurrency {
    RUB("RUB"),
    USD("USD"),
    EUR("EUR"),
    GBP("GBP"),
    HKD("HKD"),
    CHF("CHF"),
    JPY("JPY"),
    CNY("CNY"),
    TRY("TRY");

    private String value;

    CustomCurrency(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
    public String toString() {
        return String.valueOf(value);
    }
    
    public static CustomCurrency fromValue(String text) {
        for (CustomCurrency b : CustomCurrency.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
