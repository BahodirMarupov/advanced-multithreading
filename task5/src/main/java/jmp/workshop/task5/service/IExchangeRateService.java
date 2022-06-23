package jmp.workshop.task5.service;

import jmp.workshop.task5.model.Currency;
import jmp.workshop.task5.model.ExchangeRate;

import java.math.BigDecimal;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 23/06/2022
 */
public interface IExchangeRateService {

    BigDecimal getRate(Currency from, Currency to);

    void updateExchangeRate(ExchangeRate rate);
}
