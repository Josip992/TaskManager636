package com.oss.unist.hr.repository;


import com.oss.unist.hr.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

    public Optional<User> findByUsername(String username);

    @Query("SELECT u.id FROM User u WHERE u.email = :email")
    UUID findUserIdByEmail(@Param("email") String email);

    @Query("SELECT u.username FROM User u WHERE u.email = :email")
    Optional<String> findUsernameByEmail(@Param("email") String email);

    @Query("SELECT COUNT(u) FROM User u")
    int countUsers();

    List<User> findAll(Specification<User> spec, Sort sort);
}
