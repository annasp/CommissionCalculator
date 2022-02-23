package com.example.commissioncalculator.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column
    private LocalDate date;

    @Column
    private BigDecimal amount;

    @Column
    private String currency;

    @Column
    private Long clientId;

    public Transaction() {

    }

    public Transaction(
            LocalDate date,
            BigDecimal amount,
            String currency,
            Long clientId) {
        this(null, date, amount, currency, clientId);
    }

    public Transaction(
            Long id,
            LocalDate date,
            BigDecimal amount,
            String currency,
            Long clientId) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.currency = currency;
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
