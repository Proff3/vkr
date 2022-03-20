package ru.pronin.study.vkr.tradeBot.brokerAPI.exceptions;

import java.util.function.Supplier;

public class NoMarketInstrumentException extends Exception {
    public NoMarketInstrumentException() {
        super("No marketInstrument found");
    }
}
