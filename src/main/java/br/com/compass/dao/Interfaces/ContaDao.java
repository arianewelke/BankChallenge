package br.com.compass.dao.Interfaces;

import br.com.compass.entities.Conta;

public interface ContaDao {
    void insert(Conta conta);
    void update(Conta conta);
    Conta findByNumero(String numero);
    Boolean existsAccountByUsuarioIdAndTipo(int usuarioId, String tipo);
    Conta findByUsuarioIdAndNumero(int usuarioId, String numero);
}
