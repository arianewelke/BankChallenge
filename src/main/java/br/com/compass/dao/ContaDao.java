package br.com.compass.dao;

import br.com.compass.entities.Conta;

import java.util.List;

public interface ContaDao {
    void insert(Conta conta);
    void update(Conta conta);
    void deleteById(Conta conta);
    Conta findById(int id);
    List<Conta> findAll();
    Conta findByNumero(String numero);




}
