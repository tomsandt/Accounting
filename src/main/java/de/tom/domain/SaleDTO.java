package de.tom.domain;

import java.time.LocalDate;

public class SaleDTO {

    private int id;
    private Customer customerId;
    private Cost costId;
    private Article articleId;
    private int amount;
    private LocalDate dateOfSale;
    private double price;
    private double shipping;
    private double fee;
    private double reserve;
    private double profit;
    private Sale.Status status;

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

    public Sale.Status getStatus() {
        return status;
    }

    public void setStatus(Sale.Status status) {
        this.status = status;
    }
}
