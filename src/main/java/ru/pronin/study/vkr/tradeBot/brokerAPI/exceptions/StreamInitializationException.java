package ru.pronin.study.vkr.tradeBot.brokerAPI.exceptions;

public class StreamInitializationException extends Exception{
    public StreamInitializationException() {
        super("Stream has not been initialized");
    }
}
