package br.com.compass.dao;

import br.com.compass.entities.Conta;
import br.com.compass.entities.Usuario;

import java.util.List;

public interface UsuarioDao {
        void insert(Conta conta);
        void update(Conta conta);
        void deleteById(Conta conta);
        Usuario findById(int id);
        List<Usuario> findAll();
        Usuario findByCpf(String cpf);
}
