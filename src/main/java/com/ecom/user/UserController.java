package com.ecom.user;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        try {
            String registerUser=userService.registerUser(user);
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
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating user information: " + e.getMessage());
        }
    }

    // Change password endpoint
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            User user = userService.changePassword(request.getEmail(),request.getOldPassword(), request.getNewPassword());
            return ResponseEntity.ok("Password changed successfully for user: " + user.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error changing password: " + e.getMessage());
        }
    }

    // Forgot password request endpoint
    @PutMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        try {
            userService.forgotPassword(request.getEmail());
            return ResponseEntity.ok("Forgot password request processed successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing forgot password request: " + e.getMessage());
        }
    }
}
