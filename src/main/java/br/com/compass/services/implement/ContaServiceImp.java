package br.com.compass.services.implement;

import br.com.compass.dao.Interfaces.HistoricoDao;
import br.com.compass.dao.implement.ContaDaoJPA;
import br.com.compass.entities.Conta;
import br.com.compass.entities.Usuario;
import br.com.compass.services.interfaces.ContaService;
import br.com.compass.services.interfaces.HistoricoService;

import java.util.Arrays;
import java.util.List;

public class ContaServiceImp implements ContaService {
    private ContaDaoJPA dao;
    private HistoricoService historicoService;

    public ContaServiceImp(ContaDaoJPA dao, HistoricoServiceImp historicoService)
    {
        this.dao = dao;
        this.historicoService = historicoService;
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
    public Conta findByUsuarioIdAndNumero(int usuarioId, String numero) {
        if(usuarioId == 0 || numero.isEmpty()) {
            throw new IllegalArgumentException("Invalid account");
        }
        Conta conta = dao.findByUsuarioIdAndNumero(usuarioId, numero);
        if (conta == null) {
            throw new IllegalArgumentException("Conta not found");
        }
        return conta;
    }

    @Override
    public Conta findByNumero(String numero) {
        Conta conta = dao.findByNumero(numero);
        if (conta == null) {
            throw new IllegalArgumentException("Conta not found");
        }
        return conta;
    }

    @Override
    public Float amountDeposit(Conta conta, float amount) {
        if( amount < 0) {
            throw new IllegalArgumentException("Invalid value. Enter a positive value");
        }

        if (conta == null) {
            throw new IllegalArgumentException("Conta not found");
        }

        conta.deposit(amount);
        dao.update(conta);

        historicoService.registrar(conta, "deposit");

        return conta.getSaldo();
    }

    @Override
    public Float amountWithdraw(Conta conta, float amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Invalid value. Enter a positive value");
        }
        if (conta == null) {
            throw new IllegalArgumentException("Conta not found");
        }
        if (amount > conta.getSaldo()) {
            throw new IllegalArgumentException("insufficient balance.");
        }
        conta.withdraw(amount);
        dao.update(conta);

        historicoService.registrar(conta, "withdraw");

        return conta.getSaldo();
    }

    @Override
    public Conta transfer(Conta contaOrigem, String numeroContaDestino, float amount) {
        if(contaOrigem.getSaldo() < amount) {
            System.out.println("insufficient balance.");
        }

        if (amount <= 0) {
            System.out.println("Invalid value. Enter a positive value");
        }

        if(numeroContaDestino.isEmpty() || contaOrigem.getNumero().equals(numeroContaDestino)) {
            System.out.println("Invalid account");
        }

        Conta contaDestino = dao.findByNumero(numeroContaDestino);
        if(contaDestino == null) {
            System.out.println("Receiver account not found");
        }

        contaOrigem.withdraw(amount);
        contaDestino.deposit(amount);

        dao.update(contaOrigem);
        dao.update(contaDestino);

        historicoService.registrar(contaOrigem, "transfer - out");
        historicoService.registrar(contaDestino, "transfer - in");

        return contaOrigem;
    }
}