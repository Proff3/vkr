package ru.pronin.study.vkr.tradeBot.brokerAPI.exceptions;

public class EventHandlerNotInitializeException extends Exception {
    public EventHandlerNotInitializeException() {
        super("Event handler has not been initialized");
    }
}