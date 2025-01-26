package br.com.compass.dao.Interfaces;

import br.com.compass.entities.Conta;

import java.util.List;

public interface ContaDao {
    void insert(Conta conta);
    void update(Conta conta);
    void deleteById(int id);
    Conta findById(int id);
    List<Conta> findAll();
    Conta findByNumero(String numero);
    Boolean existsAccountByUsuarioIdAndTipo(int usuarioId, String tipo);
    Conta findByUsuarioIdAndNumero(int usuarioId, String numero);
}
