package de.tom.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Sale {

    public enum Status {
        COMPLETED,
        RETURNED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customerId;

    @ManyToOne
    @JoinColumn(name = "costId", nullable = false)
    private Cost costId;

    @ManyToOne
    @JoinColumn(name = "articleId", nullable = false)
    private Article articleId;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private LocalDate dateOfSale;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private double shipping;

    @Column(nullable = false)
    private double fee;

    @Column(nullable = false)
    private double reserve;

    @Column(nullable = false)
    private double profit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public Cost getCostId() {
        return costId;
    }

    public void setCostId(Cost costId) {
        this.costId = costId;
    }

    public Article getArticleId() {
        return articleId;
    }

    public void setArticleId(Article articleId) {
        this.articleId = articleId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDate getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(LocalDate dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getReserve() {
        return reserve;
    }

    public void setReserve(double reserve) {
        this.reserve = reserve;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
