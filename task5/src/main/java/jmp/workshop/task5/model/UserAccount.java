package jmp.workshop.task5.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 23/06/2022
 */
public class UserAccount implements Serializable {

    private Integer id;
    private Map<Currency, BigDecimal> account;

    public UserAccount(Integer id, Map<Currency, BigDecimal> account) {
        this.id = id;
        this.account = account;
    }

    public UserAccount() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<Currency, BigDecimal> getAccount() {
        return account;
    }

    public void setAccount(Map<Currency, BigDecimal> account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", account=" + account +
                '}';
    }
}

