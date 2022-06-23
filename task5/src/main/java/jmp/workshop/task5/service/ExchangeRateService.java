package jmp.workshop.task5.service;

import jmp.workshop.task5.model.Currency;
import jmp.workshop.task5.model.ExchangeRate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static jmp.workshop.task5.model.Currency.RUB;
import static jmp.workshop.task5.model.Currency.USD;
import static jmp.workshop.task5.model.Currency.UZS;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 23/06/2022
 */
public class ExchangeRateService implements IExchangeRateService {

    private final static Logger logger = LogManager.getLogger(ExchangeRateService.class);
    private final static List<ExchangeRate> EXCHANGE_RATES;

    private final Lock lock = new ReentrantLock();

    static {
        EXCHANGE_RATES = new ArrayList<>();
        EXCHANGE_RATES.add(new ExchangeRate(USD, RUB, BigDecimal.valueOf(53.40)));
        EXCHANGE_RATES.add(new ExchangeRate(RUB, USD, BigDecimal.valueOf(0.019)));
        EXCHANGE_RATES.add(new ExchangeRate(USD, UZS, BigDecimal.valueOf(10791.69)));
        EXCHANGE_RATES.add(new ExchangeRate(UZS, USD, BigDecimal.valueOf(0.000093)));
        EXCHANGE_RATES.add(new ExchangeRate(RUB, UZS, BigDecimal.valueOf(202.09)));
        EXCHANGE_RATES.add(new ExchangeRate(UZS, RUB, BigDecimal.valueOf(0.0049)));
    }


    @Override
    public BigDecimal getRate(Currency from, Currency to) {
        lock.lock();
        try {
            return EXCHANGE_RATES.stream().filter(e ->
                            e.getFrom().equals(from) && e.getTo().equals(to)).findFirst()
                    .orElseThrow(() -> {
                        String message = String.format("Exchange rate not found with " +
                                "currencies: from(%s), to(%s)!", from, to);
                        logger.warn(message);
                        throw new RuntimeException(message);
                    }).getRate();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void updateExchangeRate(ExchangeRate rate) {
        lock.lock();
        try {
            EXCHANGE_RATES.stream().filter(e ->
                            e.getFrom().equals(rate.getFrom()) && e.getTo().equals(rate.getTo())).findFirst()
                    .orElseThrow(() -> new RuntimeException(
                            String.format("Exchange rate not found with currencies: from(%s), to(%s)!",
                                    rate.getFrom(), rate.getTo())))
                    .setRate(rate.getRate());
        } finally {
            lock.unlock();
        }
    }
}
