package ru.pronin.study.vkr.tradeBot.brokerAPI.entities;

import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomInstrumentType;

import java.util.Objects;

public class CustomPortfolioPosition {
    private String figi = null;
    private String ticker = null;
    private String isin = null;
    private CustomInstrumentType instrumentType = null;
    private java.math.BigDecimal balance = null;
    private java.math.BigDecimal blocked = null;
    private CustomMoneyAmount expectedYield = null;
    private Integer lots = null;
    private CustomMoneyAmount averagePositionPrice = null;
    private CustomMoneyAmount averagePositionPriceNoNkd = null;
    private String name = null;

    public CustomPortfolioPosition figi(String figi) {
        this.figi = figi;
        return this;
    }



    /**
     * Get figi
     * @return figi
     **/
    public String getFigi() {
        return figi;
    }
    public void setFigi(String figi) {
        this.figi = figi;
    }
    public CustomPortfolioPosition ticker(String ticker) {
        this.ticker = ticker;
        return this;
    }



    /**
     * Get ticker
     * @return ticker
     **/
    public String getTicker() {
        return ticker;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
    public CustomPortfolioPosition isin(String isin) {
        this.isin = isin;
        return this;
    }



    /**
     * Get isin
     * @return isin
     **/
    public String getIsin() {
        return isin;
    }
    public void setIsin(String isin) {
        this.isin = isin;
    }
    public CustomPortfolioPosition instrumentType(CustomInstrumentType instrumentType) {
        this.instrumentType = instrumentType;
        return this;
    }



    /**
     * Get instrumentType
     * @return instrumentType
     **/
    public CustomInstrumentType getInstrumentType() {
        return instrumentType;
    }
    public void setInstrumentType(CustomInstrumentType instrumentType) {
        this.instrumentType = instrumentType;
    }
    public CustomPortfolioPosition balance(java.math.BigDecimal balance) {
        this.balance = balance;
        return this;
    }



    /**
     * Get balance
     * @return balance
     **/
    public java.math.BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(java.math.BigDecimal balance) {
        this.balance = balance;
    }
    public CustomPortfolioPosition blocked(java.math.BigDecimal blocked) {
        this.blocked = blocked;
        return this;
    }



    /**
     * Get blocked
     * @return blocked
     **/
    public java.math.BigDecimal getBlocked() {
        return blocked;
    }
    public void setBlocked(java.math.BigDecimal blocked) {
        this.blocked = blocked;
    }
    public CustomPortfolioPosition expectedYield(CustomMoneyAmount expectedYield) {
        this.expectedYield = expectedYield;
        return this;
    }



    /**
     * Get expectedYield
     * @return expectedYield
     **/
    public CustomMoneyAmount getExpectedYield() {
        return expectedYield;
    }
    public void setExpectedYield(CustomMoneyAmount expectedYield) {
        this.expectedYield = expectedYield;
    }
    public CustomPortfolioPosition lots(Integer lots) {
        this.lots = lots;
        return this;
    }



    /**
     * Get lots
     * @return lots
     **/
    public Integer getLots() {
        return lots;
    }
    public void setLots(Integer lots) {
        this.lots = lots;
    }
    public CustomPortfolioPosition averagePositionPrice(CustomMoneyAmount averagePositionPrice) {
        this.averagePositionPrice = averagePositionPrice;
        return this;
    }



    /**
     * Get averagePositionPrice
     * @return averagePositionPrice
     **/
    public CustomMoneyAmount getAveragePositionPrice() {
        return averagePositionPrice;
    }
    public void setAveragePositionPrice(CustomMoneyAmount averagePositionPrice) {
        this.averagePositionPrice = averagePositionPrice;
    }
    public CustomPortfolioPosition averagePositionPriceNoNkd(CustomMoneyAmount averagePositionPriceNoNkd) {
        this.averagePositionPriceNoNkd = averagePositionPriceNoNkd;
        return this;
    }



    /**
     * Get averagePositionPriceNoNkd
     * @return averagePositionPriceNoNkd
     **/
    public CustomMoneyAmount getAveragePositionPriceNoNkd() {
        return averagePositionPriceNoNkd;
    }
    public void setAveragePositionPriceNoNkd(CustomMoneyAmount averagePositionPriceNoNkd) {
        this.averagePositionPriceNoNkd = averagePositionPriceNoNkd;
    }
    public CustomPortfolioPosition name(String name) {
        this.name = name;
        return this;
    }



    /**
     * Get name
     * @return name
     **/
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomPortfolioPosition portfolioPosition = (CustomPortfolioPosition) o;
        return Objects.equals(this.figi, portfolioPosition.figi) &&
                Objects.equals(this.ticker, portfolioPosition.ticker) &&
                Objects.equals(this.isin, portfolioPosition.isin) &&
                Objects.equals(this.instrumentType, portfolioPosition.instrumentType) &&
                Objects.equals(this.balance, portfolioPosition.balance) &&
                Objects.equals(this.blocked, portfolioPosition.blocked) &&
                Objects.equals(this.expectedYield, portfolioPosition.expectedYield) &&
                Objects.equals(this.lots, portfolioPosition.lots) &&
                Objects.equals(this.averagePositionPrice, portfolioPosition.averagePositionPrice) &&
                Objects.equals(this.averagePositionPriceNoNkd, portfolioPosition.averagePositionPriceNoNkd) &&
                Objects.equals(this.name, portfolioPosition.name);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(figi, ticker, isin, instrumentType, balance, blocked, expectedYield, lots, averagePositionPrice, averagePositionPriceNoNkd, name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PortfolioPosition {\n");

        sb.append("    figi: ").append(toIndentedString(figi)).append("\n");
        sb.append("    ticker: ").append(toIndentedString(ticker)).append("\n");
        sb.append("    isin: ").append(toIndentedString(isin)).append("\n");
        sb.append("    instrumentType: ").append(toIndentedString(instrumentType)).append("\n");
        sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
        sb.append("    blocked: ").append(toIndentedString(blocked)).append("\n");
        sb.append("    expectedYield: ").append(toIndentedString(expectedYield)).append("\n");
        sb.append("    lots: ").append(toIndentedString(lots)).append("\n");
        sb.append("    averagePositionPrice: ").append(toIndentedString(averagePositionPrice)).append("\n");
        sb.append("    averagePositionPriceNoNkd: ").append(toIndentedString(averagePositionPriceNoNkd)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
