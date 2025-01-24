package br.com.compass.dao.Interfaces;

import br.com.compass.entities.Usuario;

import java.util.List;

public interface UsuarioDao {
        void insert(Usuario usuario);
        void update(Usuario usuario);
        void deleteById(int id);
        Usuario findById(int id);
        List<Usuario> findAll();
        Usuario findByCpf(String cpf);
}
