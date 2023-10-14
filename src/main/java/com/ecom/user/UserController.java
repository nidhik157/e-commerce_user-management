package com.ecom.user;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // User registration endpoint
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser( @RequestBody @Valid SignupRequest signupRequest) {
        try {
            String registerUser=userService.registerUser(signupRequest);
            System.out.println(registerUser);
            return ResponseEntity.ok(registerUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("User registration failed: " + e.getMessage());
        }
    }

    // User login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            Object userDetails = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(userDetails);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Login failed: " + e.getMessage());
        }
    }

    // Fetch user details by email endpoint
    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        try {
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Fetch all user details endpoint
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(@RequestParam(required = false) String email) {
        try {
            if (email != null) {
                User user = userService.getUserByEmail(email);
                return ResponseEntity.ok(user);
            } else {
                Iterable<User> users = userService.getAllUsers();
                return ResponseEntity.ok(users);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching user details: " + e.getMessage());
        }
    }

    // Update user information endpoint
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody User updatedUser
        ){
        try {
        	Long userId = userService.getUserIdFromToken(token,"main");
           Optional<User> existingUserOptional = userService.findById(userId); // Assuming you have a method to find the existing user by ID
             System.out.println(existingUserOptional);
           if (existingUserOptional.isEmpty()) {
               return ResponseEntity.notFound().build();
           }
           
           User existingUser = existingUserOptional.get();

            // Check and update only allowed fields
            if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getName() != null) {
                existingUser.setName(updatedUser.getName());
            }

            User savedUser = userService.updateUser(existingUser);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating user information: " + e.getMessage());
        }
    }


    // Change password endpoint
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword( @RequestHeader(name = "Authorization") String token, @RequestBody ChangePasswordRequest request) {
        try {
        	Long userId = userService.getUserIdFromToken(token,"main");
        	
           Object response=userService.changePassword(userId,request.getOldPassword(), request.getNewPassword());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error changing password: " + e.getMessage());
        }
    }

    // Forgot password request endpoint
    @PutMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        try {
           String response= userService.forgotPassword(request.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing forgot password request: " + e.getMessage());
        }
    }
    
    //Reset password request endpoint
    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestHeader(name = "Authorization") String token,@RequestBody ResetPasswordRequest request){
    	try {
    		Long userId = userService.getUserIdFromToken(token,"resetPass");
    		Object response=userService.resetPassword(userId,request.getNewPassword());
    		return ResponseEntity.ok(response);
    	}
    	catch(Exception e) {
    		return ResponseEntity.badRequest().body("Error setting password: "+e.getMessage());
    	}
    }
}
