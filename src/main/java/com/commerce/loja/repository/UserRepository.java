package com.commerce.loja.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commerce.loja.model.Employee;

public interface UserRepository  extends JpaRepository<Employee, Long>{
	Optional<Employee> findByEmail(String email);
}
