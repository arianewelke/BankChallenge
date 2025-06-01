package br.com.compass.dao.Interfaces;

import br.com.compass.entities.User;

public interface UserDao {
        void insert(User user);
        User findByCpf(String cpf);
        User findByCpfAndPassword(String cpf, String password);

}
