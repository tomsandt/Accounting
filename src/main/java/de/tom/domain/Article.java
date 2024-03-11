package de.tom.domain;

import jakarta.persistence.*;

@Entity
public class Article {

    public enum articleType {
        ELECTRONIC,
        TICKET,
        TOY,
        CLOTHING,
        OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private articleType articleType;

    @ManyToOne
    @JoinColumn(name = "dealerId", nullable = false)
    private Dealer dealerId;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Article.articleType getArticleType() {
        return articleType;
    }

    public void setArticleType(Article.articleType articleType) {
        this.articleType = articleType;
    }

    public Dealer getDealerId() {
        return dealerId;
    }

    public void setDealerId(Dealer dealerId) {
        this.dealerId = dealerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
