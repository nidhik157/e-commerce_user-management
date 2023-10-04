package com.ecom.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // You can define custom queries here if needed

    // Example of a custom query method (find a user by email)
    User findByEmail(String email);

}
