package ru.pronin.study.vkr.tradeBot.brokerAPI.enums;

public enum CustomOrderStatus {
    NEW("New"),
    PARTIALLYFILL("PartiallyFill"),
    FILL("Fill"),
    CANCELLED("Cancelled"),
    REPLACED("Replaced"),
    PENDINGCANCEL("PendingCancel"),
    REJECTED("Rejected"),
    PENDINGREPLACE("PendingReplace"),
    PENDINGNEW("PendingNew");

    private final String value;

    CustomOrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }

    public static CustomOrderStatus fromValue(String text) {
        for (CustomOrderStatus b : CustomOrderStatus.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
