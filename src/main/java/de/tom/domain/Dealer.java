package de.tom.domain;

import jakarta.persistence.*;

@Entity
public class Dealer {

    public enum dealerType {
        COMMERCIAL,
        PRIVATE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private dealerType dealerType;

    @Column(nullable = false)
    private String name;

    @Column
    private String address;

    @Column
    private String zipCode;

    @Column
    private String city;

    @Column(nullable = false)
    private String emailAddress;

    @Column
    private String phoneNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dealer.dealerType getDealerType() {
        return dealerType;
    }

    public void setDealerType(Dealer.dealerType dealerType) {
        this.dealerType = dealerType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
