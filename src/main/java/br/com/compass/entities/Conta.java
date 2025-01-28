package br.com.compass.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "conta")
public class Conta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Float saldo;
    private String tipo;
    private String numero;

    @ManyToOne //muitos-para-um
    @JoinColumn(name = "usuarioId", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
    private List<Historico> historicos = new ArrayList<>();

    public Conta() {
    }

    public Conta(String numero, float saldo, String tipo, Usuario usuario) {
        this.numero = numero;
        this.saldo = saldo;
        this.tipo = tipo;
        this.usuario = usuario;
    }


    public int getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public Float getSaldo() {
        return saldo;
    }

    public void deposit(Float amount) {
        this.saldo += amount;
    }

    public void withdraw(Float amount) {
        this.saldo -= amount;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public List<Historico> getHistoricos() {
        return historicos;
    }

    public void setHistoricos(List<Historico> historicos) {
        this.historicos = historicos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Conta conta = (Conta) o;
        return id == conta.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

}