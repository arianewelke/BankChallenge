package br.com.compass.dao.Interfaces;

import br.com.compass.entities.Account;

public interface AccountDao {
    void insert(Account account);
    void update(Account account);
    Account findByNumber(String number);
    Boolean existsAccountByUserIdAndType(int userId, String type);
    Account findByUserIdAndNumber(int userId, String number);
}
