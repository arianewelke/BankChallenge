package br.com.compass.services.interfaces;

import br.com.compass.entities.Conta;
import br.com.compass.entities.Usuario;

public interface ContaService {
    String create(float saldo, String tipo, Usuario usuario);
    Conta findByUsuarioIdAndNumero(int usuarioId, String numero);
    Conta findByNumero(String numero);
    Float amountDeposit(Conta conta, float amount);
    Float amountWithdraw(Conta conta, float amount);
    Conta transfer(Conta contaOrigem, String numeroContaDestino, float amount);

    boolean isValidAccountType(String type);
}