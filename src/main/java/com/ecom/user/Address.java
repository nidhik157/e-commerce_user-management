package com.ecom.user;

import javax.validation.constraints.NotBlank;

import jakarta.persistence.*;

@Entity
@Table(name = "Addresses")
public class Address {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id") // Assuming user_id is the foreign key column
    private Long userId; // Reference to the User entity's ID

    // Other fields
    @NotBlank(message = "name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Phone number is required")
    @Column(nullable = false)
    private String phoneNumber;
    
    @NotBlank(message = "Country is required")
    @Column(nullable = false)
    private String country;

    @NotBlank(message = "State is required")
    @Column(nullable = false)
    private String state;

    @NotBlank(message = "City is required")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "Flat no is required")
    @Column(nullable = false)
    private String flatNo;

    @NotBlank(message = "Street Address is required")
    @Column(nullable = false)
    private String streetAddress;

    @NotBlank(message = "Pincode is required")
    @Column(nullable = false)
    private Number pincode;

    @NotBlank(message = "Landmark is required")
    @Column(nullable = true)
    private String landmark;

    @Column(nullable = false)
    private Boolean isDefault;

    // Constructors
    public Address() {
        // Default constructor
    }

    public Address(Long userId, String name, String phoneNumber, String country, String state, String city,
                   String flatNo, String streetAddress, Number pincode, String landmark, Boolean isDefault) {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.state = state;
        this.city = city;
        this.flatNo = flatNo;
        this.streetAddress = streetAddress;
        this.pincode = pincode;
        this.landmark = landmark;
        this.isDefault = isDefault;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Number getPincode() {
        return pincode;
    }

    public void setPincode(Number pincode) {
        this.pincode = pincode;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }
    
    public boolean getIsDefault() {
    	return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
    	this.isDefault=isDefault;
    }
}
