package banking.fullstack.app.models;

import banking.fullstack.app.enums.AccountType;

import javax.persistence.*;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double balance;
    @Column(nullable = false)
    private Long customerId;
    @Enumerated(EnumType.STRING)
    AccountType type;
    @Column
    private double accountId = Math.floor(100000 + Math.random() * 900000);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public double getAccountId() {
        return accountId;
    }

    public void setAccountId(double accountId) {
        this.accountId = accountId;
    }

}