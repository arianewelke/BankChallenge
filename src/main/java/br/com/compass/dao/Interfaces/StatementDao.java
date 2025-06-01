package br.com.compass.dao.Interfaces;

import br.com.compass.entities.Statement;

import java.util.List;

public interface StatementDao {
    void insert(Statement statement);
    List<Statement> consultationAccount(int contaId);
}
