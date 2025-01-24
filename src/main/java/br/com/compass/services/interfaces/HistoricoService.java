package br.com.compass.services.interfaces;

import br.com.compass.entities.Conta;
import br.com.compass.entities.Historico;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoricoService {
    void registrar(int contaId, String acao, LocalDateTime dataCriacao, Float saldo);
    List<Historico> consultarPorConta(int contaId);
    List<Historico> consultarPorAcao(String acao, int contaId);
}
