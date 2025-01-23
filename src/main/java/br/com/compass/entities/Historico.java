package br.com.compass.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
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

    @ManyToOne //Muitos historicos para uma conta
    @JoinColumn(name = "contaId", nullable = false)  // Chave estrangeira para Conta
    private Conta conta;


    public Historico() {

    }

    public Historico(Conta conta, String acao, LocalDateTime dataCriacao, Float saldo) {
        this.conta = conta;
        this.acao = acao;
        this.dataCriacao = dataCriacao;
        this.saldo = saldo;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {}

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public int getId() {
        return id;
    }

    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
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