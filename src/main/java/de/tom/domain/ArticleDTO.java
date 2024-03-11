package de.tom.domain;

public class ArticleDTO {

    private int id;
    private Article.articleType articleType;
    private Dealer dealerId;
    private String name;
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
