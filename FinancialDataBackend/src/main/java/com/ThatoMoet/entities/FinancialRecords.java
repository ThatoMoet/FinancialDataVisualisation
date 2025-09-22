package com.ThatoMoet.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "financial_records")
public class FinancialRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Use IDENTITY for MySQL
    @Column(name = "record_id")
    private Long recordId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "month", nullable = false)
    private String month;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    public Long getRecordId() {
        return recordId;
    }

    public User getUser() {
        return user;
    }

    public int getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
