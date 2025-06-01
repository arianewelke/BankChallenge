package br.com.compass.services.implement;

import br.com.compass.dao.implement.UserDaoJPA;
import br.com.compass.entities.User;
import br.com.compass.exception.ExistingCPFException;
import br.com.compass.exception.InvalidDataException;
import br.com.compass.exception.UserNotFoundException;
import br.com.compass.services.interfaces.UserService;

import java.time.DateTimeException;
import java.time.LocalDate;

public class UserServiceImp implements UserService {

    private UserDaoJPA dao;
    public UserServiceImp(UserDaoJPA dao) {
        this.dao = dao;
    }

    @Override
    public User create(String name, String phone, String cpf, LocalDate dateBirth, String password) {
        if(name.isEmpty() || phone.isEmpty() || cpf.isEmpty() || dateBirth == null || password == null) {
            throw new DateTimeException("Fields must be filled in");
        }
        User userExist = dao.findByCpf(cpf);
        if(userExist != null) {
            throw new ExistingCPFException("CPF already exists");
        }

        User user = new User(name, phone, cpf, dateBirth, password);
        dao.insert(user);

        return user;
    }

    @Override
    public User findByCpfAndPassword(String cpf, String password) {
        if (cpf.isEmpty() || password.isEmpty()) {
            throw new InvalidDataException("CPF or password cannot be empty.");
        }

        User user = dao.findByCpfAndPassword(cpf, password);
        if (user == null) {
            throw new UserNotFoundException("CPF not found or invalid password.");
        }

        return user;
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
