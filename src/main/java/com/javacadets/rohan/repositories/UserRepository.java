package com.javacadets.rohan.repositories;

import com.javacadets.rohan.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.email=?1 and u.status='activated'")
    Optional<User> findByEmail(String email);
    @Query("select u from User u where u.role=?1 and u.status='activated'")
    Page<User> findAllActiveUsersByRole(String role, Pageable pageable);
    @Query("select u from User u where concat(u.email, u.firstName, u.lastName) like %?1% and u.role != 'admin'")
    Page<User> findAllActiveUsersByKey(String key, Pageable pageable);
}
