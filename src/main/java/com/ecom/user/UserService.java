package com.ecom.user;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    
    String secret = "bXllY29tbWVyY2V1c2VybWFuYWd1dG1lbW1lbnRsb2dpbnNlY3JldA==";
    String resetPasswordSecret="bXllY29tbWVyY2V1c2VybWFuYWdlbnRyZXNldHBhc3N3b3JkY2VydA==";
    Key key = new SecretKeySpec(Base64.getDecoder().decode(secret), 
                                SignatureAlgorithm.HS256.getJcaName());

    Key resetPasswordKey= new  SecretKeySpec(Base64.getDecoder().decode(resetPasswordSecret), 
            SignatureAlgorithm.HS256.getJcaName());
    
    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    // User registration method
    public String registerUser(SignupRequest userDetails) {
        // Check if the user is already present in the database
        User existingUser = userRepository.findByEmail(userDetails.getEmail());

        if (existingUser != null) {
            // User already exists
            if (existingUser.getIsDeleted()) {
                // User is deleted, show account disabled error
                throw new CustomValidationException("Account Disabled");
            } else {
                // User is not deleted, show email already registered error
                throw new CustomValidationException("Email Already Registered");
            }
        } else {
        	User user=new User();
            // User is not present in the database, proceed with registration
            // Hash the password
        	user.setEmail(userDetails.getEmail());
        	user.setName(userDetails.getName());
        	user.setRole(userDetails.getRole());
            String hashedPassword = new BCryptPasswordEncoder().encode(userDetails.getPassword());
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
                throw new CustomNotFoundException("User not found");
            } else if (user.getIsDeleted()) {
                // User is deleted
                throw new CustomValidationException("Account Disabled");
            } else if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
                // Incorrect password
                throw new CustomValidationException("Incorrect password");
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
            .setSubject(user.getId().toString())
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
    public  String changePassword(Long userId, String oldPassword, String newPassword) {
    	
        Optional<User> userOptional = userRepository.findById(userId);
        
        if (userOptional.isEmpty()) {
            throw new CustomNotFoundException("User not found");
        }
        
        User user = userOptional.get();
        
        if(user.getIsDeleted())
        {
        	throw new CustomValidationException("User account inactive");
        }
       
        if (new BCryptPasswordEncoder().matches(oldPassword, user.getPassword())) {
            // Old password matches, update the password
            String hashedPassword = new BCryptPasswordEncoder().encode(newPassword);
            user.setPassword(hashedPassword);
            user.setPasswordChangedAt(new Date());
            // Save and return the updated user
           userRepository.save(user);
            
           return  "Password changed successfully";
            
        } else {
            // Password doesn't match or user not found, return null
            throw new CustomValidationException("Old password does not match");
        }
    }

    // Forgot password method
    public String forgotPassword(String email) throws Exception {
      
      try {
    	  User user= userRepository.findByEmail(email);
    	// Generate a JWT token
          Date now = new Date();
          Date expiration = new Date(now.getTime() + 600000); // Token expires in 10 minutes (adjust as needed)

          String  token=Jwts.builder()
              .setSubject(user.getId().toString())
              .setIssuedAt(now)
              .setExpiration(expiration)
              .signWith(resetPasswordKey)
              .compact();
          String to = user.getEmail(); // Replace with the recipient's email address
          String subject = "Reset Password Request";
          String htmlContent =  loadEmailTemplate();
          String dynamicContent = htmlContent
                  .replace("{name}", user.getName())
                  .replace("{link}", "http://localhost:4000/reset-password/"+token);
          emailService.sendEmail(to, subject, dynamicContent);
          return "Forgot password request processed successfully. An email has been sent to your registered email address to reset your password.";
      } catch (Exception e) {
          throw new Exception("Email could not be sent");
      }
    }

	public String loadEmailTemplate() throws IOException {
        // Read the HTML template from a file
        Path templatePath = Paths.get("src/main/resources/resetPasswordTemplate.html");
        byte[] templateBytes = Files.readAllBytes(templatePath);
        return new String(templateBytes, StandardCharsets.UTF_8);
    }

    
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}
	
	public Long getUserIdFromToken(String token, String objective) {
		 Jws<Claims> jwt = Jwts.parserBuilder()
		            .setSigningKey(objective=="reset"?resetPasswordKey:key)
		            .build()
		            .parseClaimsJws(token.split(" ")[1]);

		    return Long.parseLong(jwt.getBody().getSubject());
		
    }
	
	//reset Password method
	public String resetPassword(Long userId,String newPassword)throws Exception{
		Optional<User> userOptional= userRepository.findById(userId);
		if (userOptional.isEmpty()) {
            throw new CustomNotFoundException("User not found");
        }
        
        User user = userOptional.get();
        
        if(user.getIsDeleted())
        {
        	throw new CustomValidationException("User account inactive");
        }
        
        String hashedPassword = new BCryptPasswordEncoder().encode(newPassword);
        user.setPassword(hashedPassword);
        user.setPasswordChangedAt(new Date());
        // Save and return the updated user
       userRepository.save(user);
        
       return "Password changed successfully" ;
		
	}
}
