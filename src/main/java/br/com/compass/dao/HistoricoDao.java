package br.com.compass.dao;

import br.com.compass.entities.Historico;

import java.util.List;

public interface HistoricoDao {
    void insert(Historico historico);
    void update(Historico historico);
    void delete(Historico historico);

    void deleteById(Historico historico);

    Historico findById(int id);
    List<Historico> findAll();
    List<Historico>findByContaId(Integer contaId);

}
