package br.com.compass.services.implement;

import br.com.compass.dao.implement.AccountDaoJPA;
import br.com.compass.entities.Account;
import br.com.compass.entities.User;
import br.com.compass.exception.*;
import br.com.compass.services.interfaces.AccountService;
import br.com.compass.services.interfaces.StatementService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AccountServiceImp implements AccountService {
    private AccountDaoJPA dao;
    private StatementService statementService;

    public AccountServiceImp(AccountDaoJPA dao, StatementServiceImp statementService)
    {
        this.dao = dao;
        this.statementService = statementService;
    }

    @Override
    public String create(float balance, String type, User user) {
        if(balance < 0 || type.isEmpty() || user == null) {
            throw new InvalidValueException("Fields must be filled in");
        }

        List<String> types = Arrays.asList("current", "savings", "salary");
        if (!types.contains(type)) {
            throw new InvalidAccountTypeException("Invalid account type");
        }

        Boolean existsAccount = dao.existsAccountByUserIdAndType(user.getId(), type);
        if(existsAccount) {
            throw new AccountAlreadyExistsException("User already has an account with the type " + type);
        }

        Account account = new Account(user.getCpf()+"-"+type, balance, type, user);
        dao.insert(account);

        return account.getNumber();
    }

    @Override
    public Account findByUsuarioIdAndNumero(int userId, String number) {
        if(userId == 0 || number.isEmpty()) {
            throw new AccountAlreadyExistsException("Invalid account");
        }
        Account account = dao.findByUserIdAndNumber(userId, number);
        if (account == null) {
            throw new AccountNotFoundException("Account not found");
        }
        return account;
    }

    @Override
    public Float amountDeposit(Account account, float amount) {
        if( amount < 0) {
            throw new InvalidValueException("Invalid value. Enter a positive value");
        }

        if (account == null) {
            throw new AccountNotFoundException("Account not found");
        }

        account.deposit(amount);
        dao.update(account);

        statementService.registrar(account, "deposit", "deposited the amount: R$ " + amount);

        return account.getBalance();
    }

    @Override
    public Float amountWithdraw(Account account, float amount) {
        if (amount < 0) {
            throw new InvalidValueException("Invalid value. Enter a positive value");
        }
        if (account == null) {
            throw new AccountNotFoundException("Account not found");
        }
        if (amount > account.getBalance()) {
            throw new InsufficientBalanceException("insufficient balance.");
        }
        account.withdraw(amount);
        dao.update(account);

        statementService.registrar(account, "withdraw", "withdrawn the amount: R$ " + amount);

        return account.getBalance();
    }

    @Override
    public Account transfer(Account accountOrigem, String destinationAccountNumber, float amount) {
        if(accountOrigem.getBalance() < amount) {
            throw new InsufficientBalanceException("insufficient balance.");
        }

        if (amount <= 0) {
            throw new InvalidValueException("Invalid value. Enter a positive value");

        }

        if(destinationAccountNumber.isEmpty() || accountOrigem.getNumber().equals(destinationAccountNumber)) {
            throw new InvalidAccountException("Invalid account number");
        }

        Account destinationAccount = dao.findByNumber(destinationAccountNumber);
        if(destinationAccount == null) {
            System.out.println("Receiver account not found");
            return null;
        }

        accountOrigem.withdraw(amount);
        destinationAccount.deposit(amount);

        dao.update(accountOrigem);
        dao.update(destinationAccount);

        statementService.registrar(accountOrigem, "transfer - out", "transfed the amount: R$ " + amount + " to " + destinationAccountNumber);
        System.out.println(accountOrigem.getNumber());
        statementService.registrar(destinationAccount, "transfer - in", "received the amount: R$ " + amount + " from " + accountOrigem.getNumber());
        System.out.println(destinationAccount.getNumber());

        return accountOrigem;
    }

    @Override
    public boolean isValidAccountType(String type) {
        Set<String> validTypes = Set.of("current", "savings", "salary");
        return validTypes.contains(type.toLowerCase());
    }
}