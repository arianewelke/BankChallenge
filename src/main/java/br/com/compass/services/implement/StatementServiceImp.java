package br.com.compass.services.implement;

import br.com.compass.dao.Interfaces.StatementDao;
import br.com.compass.dao.implement.StatementDaoJPA;
import br.com.compass.entities.Account;
import br.com.compass.entities.Statement;
import br.com.compass.exception.HistoryNotFoundException;
import br.com.compass.exception.InvalidDataException;
import br.com.compass.services.interfaces.StatementService;

import java.time.LocalDateTime;
import java.util.List;

public class StatementServiceImp implements StatementService {

    private StatementDao dao;

    public StatementServiceImp(StatementDaoJPA dao) {
        this.dao = dao;
    }

    @Override
    public void registrar(Account account, String action, String message) {
        Statement statement = new Statement(account, action, LocalDateTime.now(), account.getBalance(), message);
        dao.insert(statement);
    }

    @Override
    public List<Statement> consultarPorConta(int accountId) {
        if (accountId == 0) {
            throw new InvalidDataException("The account ID must not be 0 or null.");
        }
        List<Statement> statements = dao.consultationAccount(accountId);
        if (statements.isEmpty()) {
            throw new HistoryNotFoundException("No history found for the given account ID: " + accountId);
        }
        return statements;
    }
}
