package ru.pronin.study.vkr.tradeBot.brokerAPI.entities;

import org.springframework.stereotype.Repository;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomCurrency;

public class CustomMarketInstrument {
    private String figi;
    private String ticker;
    private String isin;
    private CustomCurrency customCurrency;
    private String name;

    public CustomMarketInstrument(String figi, String ticker, String isin, CustomCurrency customCurrency, String name) {
        this.figi = figi;
        this.ticker = ticker;
        this.isin = isin;
        this.customCurrency = customCurrency;
        this.name = name;
    }

    public String getFigi() {
        return figi;
    }

    public void setFigi(String figi) {
        this.figi = figi;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public CustomCurrency getCurrency() {
        return customCurrency;
    }

    public void setCurrency(CustomCurrency customCurrency) {
        this.customCurrency = customCurrency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
