package ru.pronin.study.vkr.tradeBot.brokerAPI.entities;

import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomCandleResolution;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

public class CustomCandle {

    //
    private Integer v = null;
    private ZonedDateTime time;
    private String figi = null;
    private CustomCandleResolution interval = null;
    private BigDecimal o = null;
    private BigDecimal c = null;
    private BigDecimal h = null;
    private BigDecimal l = null;

    public CustomCandle(String figi,
                        CustomCandleResolution interval,
                        BigDecimal o,
                        BigDecimal c,
                        BigDecimal h,
                        BigDecimal l,
                        Integer v,
                        OffsetDateTime time) {
        this.figi = figi;
        this.interval = interval;
        this.o = o;
        this.c = c;
        this.h = h;
        this.l = l;
        this.v = v;
        this.time = time.toZonedDateTime();
    }

    public CustomCandle(String figi,
                        CustomCandleResolution interval,
                        BigDecimal o,
                        BigDecimal c,
                        BigDecimal h,
                        BigDecimal l,
                        BigDecimal traidingValue,
                        ZonedDateTime dateTime) {
        this.figi = figi;
        this.interval = interval;
        this.o = o;
        this.c = c;
        this.h = h;
        this.l = l;
        this.v = traidingValue.intValue();
        this.time = dateTime;
    }

    static public CustomCandle getCandleWithNewCloseValue(CustomCandle candle, BigDecimal value) {
        return new CustomCandle(
                candle.getFigi(),
                candle.getInterval(),
                candle.getO(),
                value,
                candle.getH(),
                candle.getL(),
                candle.getV(),
                candle.getTime().toOffsetDateTime()
        );
    }

    public String toString() {
        return "class Candle {\n" +
                "    figi: " + toIndentedString(figi) + "\n" +
                "    interval: " + toIndentedString(interval) + "\n" +
                "    o: " + toIndentedString(o) + "\n" +
                "    c: " + toIndentedString(c) + "\n" +
                "    h: " + toIndentedString(h) + "\n" +
                "    l: " + toIndentedString(l) + "\n" +
                "    v: " + toIndentedString(v) + "\n" +
                "    time: " + toIndentedString(time) + "\n" +
                "}";
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    public CustomCandle(){}

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public void setV(BigDecimal v) {
        this.v = v.intValue();
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(OffsetDateTime time) {
        this.time = time.toZonedDateTime();
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public String getFigi() {
        return figi;
    }

    public void setFigi(String figi) {
        this.figi = figi;
    }

    public CustomCandleResolution getInterval() {
        return interval;
    }

    public void setInterval(CustomCandleResolution interval) {
        this.interval = interval;
    }

    public BigDecimal getO() {
        return o;
    }

    public void setO(BigDecimal o) {
        this.o = o;
    }

    public BigDecimal getC() {
        return c;
    }

    public void setC(BigDecimal c) {
        this.c = c;
    }

    public BigDecimal getH() {
        return h;
    }

    public void setH(BigDecimal h) {
        this.h = h;
    }

    public BigDecimal getL() {
        return l;
    }

    public void setL(BigDecimal l) {
        this.l = l;
    }

    public boolean isUSASession() {
        int hours = time.getHour();
        int minutes = time.getMinute();
        return (hours >= 16 && minutes >= 30) && (hours <= 23);
    }

    @Override
    public boolean equals(Object o1) {
        if (this == o1) return true;
        if (!(o1 instanceof CustomCandle that)) return false;
        return getV().equals(that.getV()) && getTime().equals(that.getTime()) && getFigi().equals(that.getFigi()) && getInterval() == that.getInterval() && getO().equals(that.getO()) && getC().equals(that.getC()) && getH().equals(that.getH()) && getL().equals(that.getL());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getV(), getTime(), getFigi(), getInterval(), getO(), getC(), getH(), getL());
    }
}
