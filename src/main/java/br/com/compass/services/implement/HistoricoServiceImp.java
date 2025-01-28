package br.com.compass.services.implement;

import br.com.compass.dao.Interfaces.HistoricoDao;
import br.com.compass.dao.implement.HistoricoDaoJPA;
import br.com.compass.entities.Conta;
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
    public void registrar(Conta conta, String acao, String mensagem) {
        Historico historico = new Historico(conta, acao, LocalDateTime.now(), conta.getSaldo(), mensagem);
        dao.insert(historico);
    }

    @Override
    public List<Historico> consultarPorConta(int contaId) {
        if (contaId == 0) {
            throw new IllegalArgumentException("The account ID must not be 0 or null.");
        }
        List<Historico> historicos = dao.consultarPorConta(contaId);
        if (historicos.isEmpty()) {
            throw new IllegalArgumentException("No history found for the given account ID: " + contaId);
        }
        return historicos;
    }
}
