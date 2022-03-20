package ru.pronin.study.vkr.tradeBot.brokerAPI.exceptions;

public class PortfolioInitializationException extends Exception{
    public PortfolioInitializationException() {
        super("Portfolio context has not been initialized");
    }
}
