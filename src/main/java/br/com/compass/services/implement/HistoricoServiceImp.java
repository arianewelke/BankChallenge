package br.com.compass.services.implement;

import br.com.compass.dao.Interfaces.HistoricoDao;
import br.com.compass.dao.implement.HistoricoDaoJPA;
import br.com.compass.entities.Historico;
import br.com.compass.services.interfaces.HistoricoService;

import java.time.LocalDateTime;
import java.util.List;

public class HistoricoServiceImp implements HistoricoService {

    private HistoricoDao dao;

    public HistoricoServiceImp(HistoricoDaoJPA dao) {
        this.dao = dao;
    }


    @Override
    public void registrar(int contaId, String acao, LocalDateTime dataCriacao, Float saldo) {
        if (contaId == 0 || acao == null || acao.trim().isEmpty()) {
            throw new IllegalArgumentException("Conta ID e ação são obrigatórios.");
        }

        if (saldo < 0) {
            throw new IllegalArgumentException("O saldo não pode ser negativo.");

        }
    }

    @Override
    public List<Historico> consultarPorConta(int contaId) {
        return List.of();
    }

    @Override
    public List<Historico> consultarPorAcao(String acao, int contaId) {
        return List.of();
    }
}
