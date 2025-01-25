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

    @Override
    public Usuario findByCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio.");
        }

        Usuario usuario = dao.findByCpf(cpf);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário com o CPF informado não encontrado.");
        }

        return usuario;
    }

    @Override
    public Usuario findByCpfAndPassword(String cpf, String senha) {
        if (cpf.isEmpty() || senha.isEmpty()) {
            throw new IllegalArgumentException("CPF e senha não podem estar vazios.");
        }

        Usuario usuario = dao.findByCpfAndPassword(cpf, senha);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado ou senha inválida.");
        }

        return usuario;
    }

}
