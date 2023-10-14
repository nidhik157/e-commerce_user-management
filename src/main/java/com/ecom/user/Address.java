package com.ecom.user;

import jakarta.persistence.*;

@Entity
@Table(name = "Addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private String phoneNumber;
    private String country;
    private String state;
    private String city;
    private String flatNo;
    private String streetAddress;
    private String pincode;
    private String landmark;
    private boolean isActive;

    public Address() {
        // Default constructor
    }

    public Address(User user, String name, String phoneNumber, String country, String state, String city, String flatNo, String streetAddress, String pincode, String landmark, Boolean isActive) {
        this.user = user;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.state = state;
        this.city = city;
        this.flatNo = flatNo;
        this.streetAddress = streetAddress;
        this.pincode = pincode;
        this.landmark = landmark;
        this.isActive=isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }
    
    public boolean getIsActive() {
    	return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
    	this.isActive=isActive;
    }
}
