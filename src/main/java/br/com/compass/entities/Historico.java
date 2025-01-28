package br.com.compass.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico")
public class Historico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String acao;
    private LocalDateTime dataCriacao;
    private Float saldo;
    private String mensagem;

    @ManyToOne //Muitos historicos para uma conta
    @JoinColumn(name = "contaId", nullable = false)  // Chave estrangeira para Conta
    private Conta conta;


    public Historico() {
    }

    public Historico(Conta conta, String acao, LocalDateTime dataCriacao, Float saldo, String mensagem) {
        this.conta = conta;
        this.acao = acao;
        this.dataCriacao = dataCriacao;
        this.saldo = saldo;
        this.mensagem = mensagem;
    }

    public String getAcao() {
        return acao;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {}

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    public String getMensagem() { return mensagem; }

    public int getId() {
        return id;
    }

    public Float getSaldo() {
        return saldo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Historico historico = (Historico) o;
        return id == historico.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}