package de.tom.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "product_return")
public class Return {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customerId;

    @ManyToOne
    @JoinColumn(name = "saleId", nullable = false)
    private Sale saleId;

    @Column(nullable = false)
    private int amount;

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

    public Sale getSaleId() {
        return saleId;
    }

    public void setSaleId(Sale saleId) {
        this.saleId = saleId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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
