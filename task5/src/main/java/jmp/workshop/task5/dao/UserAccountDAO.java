package jmp.workshop.task5.dao;

import jmp.workshop.task5.model.UserAccount;
import jmp.workshop.task5.util.ValidationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 23/06/2022
 */
public class UserAccountDAO implements IUserAccountDAO {

    private final static Logger logger = LogManager.getLogger(UserAccountDAO.class);

    @Override
    public void save(UserAccount userAccount) {
        ValidationUtils.validateNotNull(userAccount, "UserAccount cannot be null!");
        File file = new File(generatePath(userAccount.getId()));
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(userAccount);
            logger.info("User account with id({}) is written to file!", userAccount.getId());
        } catch (IOException ex) {
            logger.error("Exception in saving user account with id({}) to file: {}",
                    userAccount.getId(), ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public Optional<UserAccount> findById(Integer userId) {
        ValidationUtils.validateNotNull(userId, "User id cannot be null!");
        File file = new File(generatePath(userId));
        if (!file.exists()) {
            return Optional.empty();
        }
        try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(file))) {
            return Optional.of((UserAccount) oos.readObject());
        } catch (IOException | ClassNotFoundException ex) {
            logger.error("Exception in saving user account with id({}) to file: {}",
                    userId, ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    private String generatePath(Integer id) {
        return "user-account-" + id.toString() + ".txt";
    }
}
