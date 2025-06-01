package br.com.compass.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Float balance;
    private String type;
    private String number;

    //muitos usuarios-para-uma conta
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    //uma conta-para-muitos historicos
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Statement> statements = new ArrayList<>();

    public Account() {
    }

    public Account(String number, float balance, String type, User user) {
        this.number = number;
        this.balance = balance;
        this.type = type;
        this.user = user;
    }


    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Float getBalance() {
        return balance;
    }

    public void deposit(Float amount) {
        this.balance += amount;
    }

    public void withdraw(Float amount) {
        this.balance -= amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public List<Statement> getStatement() {
        return statements;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;
        return id == account.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

}