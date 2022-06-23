package jmp.workshop.task5.dao;

import jmp.workshop.task5.model.UserAccount;

import java.util.Optional;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 23/06/2022
 */
public interface IUserAccountDAO {

    void save(UserAccount userAccount);

    Optional<UserAccount> findById(Integer userId);
}
