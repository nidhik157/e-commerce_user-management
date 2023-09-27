package com.example.user;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "Users") // Specify the table name explicitly
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Column(nullable = false, unique = true) // Add constraints for email
    private String email;

    @Column(nullable = false) // Add constraints for name
    private String name;

    @Column(name = "phone_number") // Specify the column name explicitly
    private String phoneNumber;

    @Column(nullable = false) // Add constraints for role
    private String role;

    @Column(nullable = false) // Add constraints for password
    private String password;
    
    @Column(nullable=false) //Add constraints for password_changedAt
    private Date passwordChangedAt;
    
    @Column(nullable = false, columnDefinition = "boolean default false") // Add constraints for isDeleted column
    private Boolean isDeleted=false;
    
    // Constructors
    public User() {
        // Default constructor
    }

    public User(String email, String name, String phoneNumber, String role, String password, Date passwordChangedAt, Boolean isDeleted) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.password = password;
        this.passwordChangedAt= passwordChangedAt;
        this.isDeleted=isDeleted;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public void setPasswordChangedAt(Date date) {
		this.passwordChangedAt = date;
		
	}
	
	public Date getPasswordChangedAt() {
		return passwordChangedAt;
	}
	
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted=isDeleted;
	}
	
	public Boolean getIsDeleted() {
		return isDeleted;
	}
}
