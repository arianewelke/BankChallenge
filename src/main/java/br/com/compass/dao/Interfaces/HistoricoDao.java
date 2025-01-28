package br.com.compass.dao.Interfaces;

import br.com.compass.entities.Historico;

import java.util.List;

public interface HistoricoDao {
    void insert(Historico historico);
    List<Historico> consultarPorConta(int contaId);
}
