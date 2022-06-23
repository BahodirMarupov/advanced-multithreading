package jmp.workshop.task5.service;

import jmp.workshop.task5.model.Currency;
import jmp.workshop.task5.model.ExchangeRequest;
import jmp.workshop.task5.model.UserAccount;

import java.math.BigDecimal;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 23/06/2022
 */
public interface IUserAccountService {

    void createUserAccount(UserAccount userAccount);

    UserAccount getUserAccount(Integer userId);

    void exchangeMoney(ExchangeRequest exchangeRequest);
}
