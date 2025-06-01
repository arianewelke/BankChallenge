package br.com.compass.services.interfaces;

import br.com.compass.entities.Account;
import br.com.compass.entities.User;

public interface AccountService {
    String create(float saldo, String tipo, User user);
    Account findByUsuarioIdAndNumero(int usuarioId, String numero);
    Float amountDeposit(Account account, float amount);
    Float amountWithdraw(Account account, float amount);
    Account transfer(Account accountOrigem, String numeroContaDestino, float amount);
    boolean isValidAccountType(String type);
}