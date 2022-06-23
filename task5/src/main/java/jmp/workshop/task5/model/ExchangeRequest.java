package jmp.workshop.task5.model;

import java.math.BigDecimal;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 23/06/2022
 */
public class ExchangeRequest {
    private Integer userId;
    private Currency from;
    private Currency to;
    private BigDecimal amount;

    public ExchangeRequest(Integer userId, Currency from, Currency to, BigDecimal amount) {
        this.userId = userId;
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public ExchangeRequest() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
