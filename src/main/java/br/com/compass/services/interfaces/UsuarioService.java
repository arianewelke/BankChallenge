package br.com.compass.services.interfaces;

import br.com.compass.entities.Usuario;

import java.time.LocalDate;

public interface UsuarioService {
    Usuario create(String nome, String telefone, String cpf, LocalDate dataNascimento, String senha);
    //Usuario findByCpf(String cpf);
    Usuario findByCpfAndPassword(String cpf, String senha);

    boolean isValidName(String name);

    boolean isValidCpf(String cpf);

    boolean isValidPhone(String phone);

    boolean isValidPassword(String password);
}

