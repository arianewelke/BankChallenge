package br.com.compass.services.implement;

import br.com.compass.dao.implement.UsuarioDaoJPA;
import br.com.compass.entities.Usuario;
import br.com.compass.services.interfaces.UsuarioService;

import java.time.LocalDate;

public class UsuarioServiceImp implements UsuarioService {

    private UsuarioDaoJPA dao;

    public UsuarioServiceImp(UsuarioDaoJPA dao) {
        this.dao = dao;
    }

    @Override
    public Usuario create(String nome, String telefone, String cpf, LocalDate dataNascimento, String senha) {
        if(nome.isEmpty() || telefone.isEmpty() || cpf.isEmpty() || dataNascimento == null || senha == null) {
            throw new IllegalArgumentException("Os campos devem ser preenchidos");
        }

        Usuario usuario = new Usuario(nome, telefone, cpf, dataNascimento, senha);
        dao.insert(usuario);

        return usuario;
    }
}
