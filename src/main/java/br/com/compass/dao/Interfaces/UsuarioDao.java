package br.com.compass.dao.Interfaces;

import br.com.compass.entities.Usuario;

import java.util.List;

public interface UsuarioDao {
        void insert(Usuario usuario);
        Usuario findByCpf(String cpf);
        Usuario findByCpfAndPassword(String cpf, String senha);

}
