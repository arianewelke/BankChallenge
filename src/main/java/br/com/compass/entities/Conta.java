package br.com.compass.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "conta")
public class Conta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Float saldo;
    private String tipo;
    private String numero;

    @OneToOne
    @JoinColumn(name = "usuarioId", unique = true, nullable = false)  // Relacionamento um-para-um
    private Usuario usuario;

    public Conta() {
    }

    public Conta(String numero, float saldo, String tipo, Usuario usuario) {
        this.numero = numero;
        this.saldo = saldo;
        this.tipo = tipo;
        this.usuario = usuario;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
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