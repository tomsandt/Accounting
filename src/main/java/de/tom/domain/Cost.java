package de.tom.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Cost {

    public enum expenditureType {
        LICENSE,
        PACKAGING,
        PRODUCT,
        OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private expenditureType expenditureType;

    @ManyToOne
    @JoinColumn(name = "articleId", nullable = false)
    private Article articleId;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private LocalDate purchaseDate;

    @Column(nullable = false)
    private double shipping;

    @Column(nullable = false)
    private double tax;

    @Column(nullable = false)
    private double totalPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cost.expenditureType getExpenditureType() {
        return expenditureType;
    }

    public void setExpenditureType(Cost.expenditureType expenditureType) {
        this.expenditureType = expenditureType;
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

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
