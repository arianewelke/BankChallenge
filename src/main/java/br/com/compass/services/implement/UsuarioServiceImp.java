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
        Usuario usuarioExisixt = dao.findByCpf(cpf);
        if(usuarioExisixt != null) {
            throw new IllegalArgumentException("CPF already exists");
        }

        Usuario usuario = new Usuario(nome, telefone, cpf, dataNascimento, senha);
        dao.insert(usuario);

        return usuario;
    }

    @Override
    public Usuario findByCpfAndPassword(String cpf, String senha) {
        if (cpf.isEmpty() || senha.isEmpty()) {
            throw new IllegalArgumentException("CPF or password cannot be empty.");
        }

        Usuario usuario = dao.findByCpfAndPassword(cpf, senha);
        if (usuario == null) {
            throw new IllegalArgumentException("CPF not found or invalid password.");
        }

        return usuario;
    }

    @Override
    public boolean isValidName(String name) {
        return name != null && name.length() >= 3 && name.matches("[a-zA-ZÀ-ú\\s]+");
    }

    @Override
    public boolean isValidCpf(String cpf) {
        return cpf.matches("\\d{11}");
    }

    @Override
    public boolean isValidPhone(String phone) {
        return phone.matches("\\d{10,11}");
    }
    @Override
    public boolean isValidPassword(String password) {
        return password.matches("\\d{4}");
    }

}
