package jmp.workshop.task5.service;

import jmp.workshop.task5.dao.IUserAccountDAO;
import jmp.workshop.task5.dao.UserAccountDAO;
import jmp.workshop.task5.exception.UserNotFoundException;
import jmp.workshop.task5.model.Currency;
import jmp.workshop.task5.model.ExchangeRequest;
import jmp.workshop.task5.model.UserAccount;
import jmp.workshop.task5.util.ValidationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 23/06/2022
 */
public class UserAccountService implements IUserAccountService {

    private final static Logger logger = LogManager.getLogger(UserAccountService.class);
    private final static Map<Integer, Object> locks = new HashMap<>();

    private final IUserAccountDAO userAccountDAO = new UserAccountDAO();
    private final IExchangeRateService exchangeRateService = new ExchangeRateService();


    @Override
    public void createUserAccount(UserAccount userAccount) {
        ValidationUtils.validateUserAccount(userAccount);

        synchronized (getSyncObjectForId(userAccount.getId())) {
            if (userAccountDAO.findById(userAccount.getId()).isPresent()) {
                throw new RuntimeException(String.format("User with id %s already saved", userAccount.getId()));
            }
            userAccountDAO.save(userAccount);
            logger.info("User account with id({}) is saved!", userAccount.getId());
        }
    }

    @Override
    public UserAccount getUserAccount(Integer userId) {
        ValidationUtils.validateNotNull(userId, "UserId cannot be null!");

        synchronized (getSyncObjectForId(userId)) {
            return userAccountDAO.findById(userId).orElseThrow(() ->
                    new UserNotFoundException(String.format("User with id(%s) is not found!", userId)));
        }
    }

    @Override
    public void exchangeMoney(ExchangeRequest request) {
        ValidationUtils.validateNotNull(request, "Exchange request cannot be null!");
        Currency from = request.getFrom();
        Currency to = request.getTo();
        BigDecimal amount = request.getAmount();
        ValidationUtils.validateParameters(List.of(from, to, amount));

        synchronized (getSyncObjectForId(request.getUserId())) {
            UserAccount userAccount = userAccountDAO.findById(request.getUserId()).orElseThrow(() ->
                    new UserNotFoundException(String.format("User with id(%s) is not found!", request.getUserId())));

            checkExistenceOfCurrenciesInUserAccounts(userAccount, List.of(from, to));
            checkUserHasEnoughMoneyToExchange(userAccount, from, amount);

            BigDecimal rate = exchangeRateService.getRate(from, to);
            logger.info("Exchange rate from {} to {} is {}", from, to, rate);

            BigDecimal amountInFrom = userAccount.getAccount().get(from);
            userAccount.getAccount().put(from, amountInFrom.subtract(amount));

            BigDecimal amountInTo = userAccount.getAccount().get(to);
            userAccount.getAccount().put(to, amountInTo.add(amount.multiply(rate)));
            logger.info("Successfully exchanged from {} to {} for user account with id({})",
                    from, to, userAccount.getId());

            userAccountDAO.save(userAccount);
        }
    }

    static Object getSyncObjectForId(Integer id) {
        synchronized (locks) {
            return locks.computeIfAbsent(id, k -> new Object());
        }
    }

    private void checkExistenceOfCurrenciesInUserAccounts(UserAccount userAccount, List<Currency> currencies) {
        boolean exist = userAccount.getAccount().keySet().stream().filter(currencies::contains).count() == 2;
        if (!exist) {
            String message = String.format("User with id(%s) has not accounts with these currencies %s!",
                    userAccount.getId(), currencies);
            logger.warn(message);
            throw new RuntimeException(message);
        }
    }

    private void checkUserHasEnoughMoneyToExchange(UserAccount userAccount, Currency from, BigDecimal amount) {
        boolean enough = userAccount.getAccount().get(from).compareTo(amount) > 0;
        if (!enough) {
            String message = String.format("User with id(%s) has not enough money to exchange in account " +
                    "with currency %s!", userAccount.getId(), from);
            logger.warn(message);
            throw new RuntimeException(message);
        }
    }
}
