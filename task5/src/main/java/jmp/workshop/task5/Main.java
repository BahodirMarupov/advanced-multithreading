package jmp.workshop.task5;

import jmp.workshop.task5.model.Currency;
import jmp.workshop.task5.model.ExchangeRequest;
import jmp.workshop.task5.model.UserAccount;
import jmp.workshop.task5.service.IUserAccountService;
import jmp.workshop.task5.service.UserAccountService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static jmp.workshop.task5.model.Currency.RUB;
import static jmp.workshop.task5.model.Currency.USD;
import static jmp.workshop.task5.model.Currency.UZS;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 23/06/2022
 */
public class Main {

    private final static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        IUserAccountService userAccountService = new UserAccountService();

        UserAccount firstUserAccount = generateUserAccount(1);
        UserAccount secondUserAccount = generateUserAccount(2);
        UserAccount thirdUserAccount = generateUserAccount(3);

        userAccountService.createUserAccount(firstUserAccount);
        userAccountService.createUserAccount(secondUserAccount);
        userAccountService.createUserAccount(thirdUserAccount);

        firstUserAccount = userAccountService.getUserAccount(firstUserAccount.getId());
        System.out.println(firstUserAccount);
        secondUserAccount = userAccountService.getUserAccount(secondUserAccount.getId());
        System.out.println(secondUserAccount);
        thirdUserAccount = userAccountService.getUserAccount(thirdUserAccount.getId());
        System.out.println(thirdUserAccount);

        ExecutorService executorService = Executors.newFixedThreadPool(6);
        for (ExchangeRequest exchangeRequest : generateExchangeRequest()) {
            executorService.submit(() -> userAccountService.exchangeMoney(exchangeRequest));
        }

        executorService.shutdown();
    }

    private static UserAccount generateUserAccount(Integer id) {
        Map<Currency, BigDecimal> accounts = new Hashtable<>();
        accounts.put(USD, BigDecimal.valueOf(random.nextInt(10000)));
        accounts.put(RUB, BigDecimal.valueOf(random.nextInt(10000)));
        accounts.put(UZS, BigDecimal.valueOf(random.nextInt(10000000)));
        return new UserAccount(id, accounts);
    }

    private static List<ExchangeRequest> generateExchangeRequest() {
        List<ExchangeRequest> requests = new ArrayList<>();
        requests.add(new ExchangeRequest(1, USD, UZS, BigDecimal.valueOf(100)));
        requests.add(new ExchangeRequest(1, RUB, USD, BigDecimal.valueOf(200)));
        requests.add(new ExchangeRequest(2, RUB, USD, BigDecimal.valueOf(300)));
        requests.add(new ExchangeRequest(2, UZS, RUB, BigDecimal.valueOf(10000)));
        requests.add(new ExchangeRequest(3, USD, RUB, BigDecimal.valueOf(250)));
        requests.add(new ExchangeRequest(3, RUB, UZS, BigDecimal.valueOf(125)));
        return requests;
    }

}
