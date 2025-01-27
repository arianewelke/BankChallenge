package br.com.compass.dao.Interfaces;

import br.com.compass.entities.Historico;

import java.util.List;

public interface HistoricoDao {
    void insert(Historico historico);
    void update(Historico historico);
    void deleteById(Historico historico);
    Historico findById(int id);
    //List<Historico>findByAcao(String acao);
    List<Historico> consultarPorConta(int contaId);
}
