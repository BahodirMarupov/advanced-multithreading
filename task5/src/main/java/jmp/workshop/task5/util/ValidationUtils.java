package jmp.workshop.task5.util;

import jmp.workshop.task5.exception.ValidationException;
import jmp.workshop.task5.model.UserAccount;

import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 23/06/2022
 */
public class ValidationUtils {

    public static void validateUserAccount(UserAccount userAccount) {
        checkCondition(isNull(userAccount), "UserAccount cannot be null!");
        checkCondition(isNull(userAccount.getAccount()) || userAccount.getAccount().isEmpty(),
                "User should have at least one account!");
        checkCondition(isNull(userAccount.getId()), "UserAccount id cannot be null!");
    }

    public static void validateNotNull(Object object, String message) {
        checkCondition(isNull(object), message);
    }

    public static void validateParameters(List<Object> parameters) {
        for (Object parameter : parameters) {
            checkCondition(isNull(parameter), "INVALID_DATA");
            checkCondition(parameter instanceof String
                    && isBlank((String) parameter), "INVALID_DATA");
            checkCondition(parameter instanceof Collection
                    && ((Collection<?>) parameter).isEmpty(), "INVALID_DATA");
        }
    }

    private static void checkCondition(boolean isFail, String message) {
        if (isFail) throw new ValidationException(message);
    }
}
