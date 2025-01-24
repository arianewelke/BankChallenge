package br.com.compass.services.implement;

import br.com.compass.dao.implement.ContaDaoJPA;
import br.com.compass.entities.Conta;
import br.com.compass.entities.Usuario;
import br.com.compass.services.interfaces.ContaService;
import java.util.Arrays;
import java.util.List;

public class ContaServiceImp implements ContaService {
    private ContaDaoJPA dao;

    public ContaServiceImp(ContaDaoJPA dao) {
        this.dao = dao;
    }

    @Override
    public String create(float saldo, String tipo, Usuario usuario) {
        if(saldo < 0 || tipo.isEmpty() || usuario == null) {
            throw new IllegalArgumentException("Os campos devem ser preenchidos");
        }

        List<String> tipos = Arrays.asList("corrente","poupanca","salario");
        if(tipos.contains(tipo) == false) {
            throw new IllegalArgumentException("Tipo de conta inválido");
        }

        Boolean existsAccount = dao.existsAccountByUsuarioIdAndTipo(usuario.getId(), tipo);
        if(existsAccount == true) {
            throw new IllegalArgumentException("Usuario já possui uma conta com o tipo " + tipo);
        }

        Conta conta = new Conta(usuario.getCpf()+"-"+tipo, saldo, tipo, usuario);
        dao.insert(conta);

        return conta.getNumero();
    }
}