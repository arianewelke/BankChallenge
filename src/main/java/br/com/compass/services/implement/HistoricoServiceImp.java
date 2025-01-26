package br.com.compass.services.implement;

import br.com.compass.dao.Interfaces.HistoricoDao;
import br.com.compass.dao.implement.HistoricoDaoJPA;
import br.com.compass.entities.Historico;
import br.com.compass.services.interfaces.HistoricoService;

import java.time.LocalDateTime;
import java.util.IllegalFormatCodePointException;
import java.util.List;

public class HistoricoServiceImp implements HistoricoService {

    private HistoricoDao dao;

    public HistoricoServiceImp(HistoricoDaoJPA dao) {
        this.dao = dao;
    }


    @Override
    public void registrar(int contaId, String acao, LocalDateTime dataCriacao, Float saldo) {
        if (contaId == 0 || acao == null || acao.trim().isEmpty()) {
            throw new IllegalArgumentException("Account ID and action are required.");
        }

        if (saldo < 0) {
            throw new IllegalArgumentException("The balance cannot be negative.");

        }
    }

    @Override
    public List<Historico> consultarPorConta(int contaId) {
        if (contaId == 0) {
            throw new IllegalArgumentException("Account is required.");
        }
        List<Historico> historico = dao.findByContaId(contaId);
        if (historico == null || historico.isEmpty()) {
            throw new IllegalArgumentException("No history found for the given account.");
        }

        return historico;
    }

    @Override
    public List<Historico> consultarPorAcao(String acao, int contaId) {
        return List.of();
    }
}
