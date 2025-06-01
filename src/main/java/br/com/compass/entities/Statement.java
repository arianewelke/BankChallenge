package br.com.compass.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "statement")
public class Statement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String action;
    private LocalDateTime dateCreation;
    private Float balance;
    private String message;

    @ManyToOne //Muitos historicos para uma conta
    @JoinColumn(name = "accountId", nullable = false)  // Chave estrangeira para Conta
    private Account account;


    public Statement() {
    }

    public Statement(Account account, String action, LocalDateTime dateCreation, Float balance, String message) {
        this.account = account;
        this.action = action;
        this.dateCreation = dateCreation;
        this.balance = balance;
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {}

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
    public String getMessage() { return message; }

    public int getId() {
        return id;
    }

    public Float getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Statement statement = (Statement) o;
        return id == statement.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}