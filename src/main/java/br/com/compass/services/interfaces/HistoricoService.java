package br.com.compass.services.interfaces;

import br.com.compass.entities.Conta;
import br.com.compass.entities.Historico;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoricoService {
    void registrar(Conta conta, String acao, String mensagem);

    List<Historico> consultarPorConta(int contaId);
}
