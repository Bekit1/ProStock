package net.ukr.bekit.model;

import javax.persistence.*;
import java.text.DecimalFormat;


@Entity
@Table(name = "Accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "account")
    private CustomUser user;

    @Column(name = "balance")
    private double balance;

    private String stringBalance;

    @Column(name = "currency")
    private String currency = "USD";

    public Account() {
        this.balance = 3000;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public String getStringBalance() {
        return this.stringBalance = new DecimalFormat("#0.00").format(this.getBalance());
    }
}
