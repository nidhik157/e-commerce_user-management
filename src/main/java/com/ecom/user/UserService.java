package com.ecom.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class UserService {
    private final UserRepository userRepository;
   private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // User registration method
    public String registerUser(User user) {
        // Check if the user is already present in the database
        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            // User already exists
            if (existingUser.getIsDeleted()) {
                // User is deleted, show account disabled error
                return "Account Disabled";
            } else {
                // User is not deleted, show email already registered error
                return "Email Already Registered";
            }
        } else {
            // User is not present in the database, proceed with registration
            // Hash the password
            String hashedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
            user.setPassword(hashedPassword);
            
            // Update the password_changed_at field with the current date
            user.setPasswordChangedAt(new Date());

            // Save the user to the repository
            userRepository.save(user);

            // Return successful message
            return "Signup Successful";
        }
    }

    // User login method
    
        public Map<String, Object> loginUser(String email, String password) {
            Map<String, Object> response = new HashMap<>();

            // Find the user by email
            User user = userRepository.findByEmail(email);


            if (user == null) {
                // User not found
                response.put("error", "User not found");
            } else if (user.getIsDeleted()) {
                // User is deleted
                response.put("error", "Account is disabled");
            } else if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
                // Incorrect password
                response.put("error", "Incorrect password");
            } else {
                // Password matches and user is not deleted
                // Generate a token
                String token = generateToken(user);

                // Add user and token to the response map
                response.put("user", user);
                response.put("token", token);
            }

            return response;
        }
    


	private String generateToken(User user) {
        // Generate a JWT token
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 86400000); // Token expires in 24 hours (adjust as needed)

        return Jwts.builder()
            .setSubject(user.getEmail())
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(key)
            .compact();
    }


    // Fetch user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Fetch all users
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update user information
    public User updateUser(User user) {
        // Implement user update logic, e.g., updating name, phone number, etc.
        return userRepository.save(user);
    }

    // Change password method
    public User changePassword(String email, String oldPassword, String newPassword) {
        User user = userRepository.findByEmail(email);
        
        if (user != null && new BCryptPasswordEncoder().matches(oldPassword, user.getPassword())) {
            // Old password matches, update the password
            String hashedPassword = new BCryptPasswordEncoder().encode(newPassword);
            user.setPassword(hashedPassword);
            
            // Save and return the updated user
            return userRepository.save(user);
        } else {
            // Password doesn't match or user not found, return null
            return null;
        }
    }

    // Forgot password method
    public void forgotPassword(String email) {
        // Implement forgot password logic, e.g., sending a reset email
        // For simplicity, we'll leave this as a stub
    }
}
