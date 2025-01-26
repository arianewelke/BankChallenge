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
            throw new IllegalArgumentException("Fields must be filled in");
        }

        List<String> tipos = Arrays.asList("current", "savings", "salary");
        if(tipos.contains(tipo) == false) {
            throw new IllegalArgumentException("Invalid account type");
        }

        Boolean existsAccount = dao.existsAccountByUsuarioIdAndTipo(usuario.getId(), tipo);
        if(existsAccount == true) {
            throw new IllegalArgumentException("User already has an account with the type " + tipo);
        }

        Conta conta = new Conta(usuario.getCpf()+"-"+tipo, saldo, tipo, usuario);
        dao.insert(conta);

        return conta.getNumero();
    }

    @Override
    public String findByUsuarioIdAndNumero(int usuarioId, String numero) {
        if(usuarioId == 0 || numero.isEmpty()) {
            throw new IllegalArgumentException("Invalid account");
        }
        Conta conta = dao.findByUsuarioIdAndNumero(usuarioId, numero);
        if (conta == null) {
            throw new IllegalArgumentException("Conta not found");
        }
        return conta.getNumero();
    }
}