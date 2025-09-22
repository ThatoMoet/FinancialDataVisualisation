package com.ThatoMoet.repository;

import com.ThatoMoet.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
