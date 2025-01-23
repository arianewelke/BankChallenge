package br.com.compass.dao;

import br.com.compass.entities.Usuario;

import java.util.List;

public interface UsuarioDao {
        void insert(Usuario usuario);
        void update(Usuario usuario);
        void deleteById(Usuario usuario);
        Usuario findById(int id);

        Usuario findById(Integer id);

        List<Usuario> findAll();
        Usuario findByCpf(String cpf);
}
