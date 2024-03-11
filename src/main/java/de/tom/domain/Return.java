package de.tom.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Return {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customerId;

    @ManyToOne
    @JoinColumn(name = "articleId", nullable = false)
    private Article articleId;

    @ManyToOne
    @JoinColumn(name = "saleId", nullable = false)
    private Sale saleId;

    @Column(nullable = false)
    private LocalDate returnDate;

    @Column
    private String note;

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

    public Article getArticleId() {
        return articleId;
    }

    public void setArticleId(Article articleId) {
        this.articleId = articleId;
    }

    public Sale getSaleId() {
        return saleId;
    }

    public void setSaleId(Sale saleId) {
        this.saleId = saleId;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
