package br.com.compass.services.interfaces;

import br.com.compass.entities.User;

import java.time.LocalDate;

public interface UserService {
    User create(String nome, String telefone, String cpf, LocalDate dataNascimento, String senha);
    User findByCpfAndPassword(String cpf, String senha);
    boolean isValidName(String name);
    boolean isValidCpf(String cpf);
    boolean isValidPhone(String phone);
    boolean isValidPassword(String password);
}

