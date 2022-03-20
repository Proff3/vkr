package ru.pronin.study.vkr.tradeBot.brokerAPI.exceptions;

public class CandlesNotFoundException extends Exception{
    public CandlesNotFoundException() {
        super("Candles not found");
    }
}
