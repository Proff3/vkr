package ru.pronin.study.vkr.tradeBot.brokerAPI.exceptions;

public class WrongStrategyInitializationException extends Exception{
    public WrongStrategyInitializationException() {
        super("Wrong strategy initialization");
    }
}
