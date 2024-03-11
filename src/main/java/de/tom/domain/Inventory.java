package de.tom.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Inventory {

    @Id
    private int id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "articleId")
    private Article articleId;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private LocalDate purchaseDate;

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
}
