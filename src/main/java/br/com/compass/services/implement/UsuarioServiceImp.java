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
            throw new IllegalArgumentException("Fields must be filled in");
        }

        Usuario usuario = new Usuario(nome, telefone, cpf, dataNascimento, senha);
        dao.insert(usuario);

        return usuario;
    }

    @Override
    public Usuario findByCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF cannot be empty.");
        }

        Usuario usuario = dao.findByCpf(cpf);
        if (usuario == null) {
            throw new IllegalArgumentException("User with the provided CPF not found.");
        }

        return usuario;
    }

    @Override
    public Usuario findByCpfAndPassword(String cpf, String senha) {
        if (cpf.isEmpty() || senha.isEmpty()) {
            throw new IllegalArgumentException("CPF and password cannot be empty.");
        }

        Usuario usuario = dao.findByCpfAndPassword(cpf, senha);
        if (usuario == null) {
            throw new IllegalArgumentException("Username not found or invalid password.");
        }

        return usuario;
    }

}
