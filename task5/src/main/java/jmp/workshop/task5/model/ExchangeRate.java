package jmp.workshop.task5.model;

import java.math.BigDecimal;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 23/06/2022
 */
public class ExchangeRate {
    private Currency from;
    private Currency to;
    private BigDecimal rate;

    public ExchangeRate(Currency from, Currency to, BigDecimal rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    public ExchangeRate() {
    }

    public Currency getFrom() {
        return from;
    }

    public void setFrom(Currency from) {
        this.from = from;
    }

    public Currency getTo() {
        return to;
    }

    public void setTo(Currency to) {
        this.to = to;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
