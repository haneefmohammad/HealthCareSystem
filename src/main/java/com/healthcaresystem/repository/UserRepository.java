package com.healthcaresystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcaresystem.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByUserEmailIgnoreCase(String userEmail);

}
