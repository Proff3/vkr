package ru.pronin.study.vkr.tradeBot.brokerAPI.exceptions;

public class OrdersContextInitializationException extends Exception{
    public OrdersContextInitializationException() {
        super("Orders context has not been initialized");
    }
}
