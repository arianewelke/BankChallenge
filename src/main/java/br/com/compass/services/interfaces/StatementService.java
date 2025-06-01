package br.com.compass.services.interfaces;

import br.com.compass.entities.Account;
import br.com.compass.entities.Statement;

import java.util.List;

public interface StatementService {
    void registrar(Account account, String acao, String mensagem);

    List<Statement> consultarPorConta(int contaId);
}
