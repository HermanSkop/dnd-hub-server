package com.example.dndhub.repositories;

import com.example.dndhub.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}