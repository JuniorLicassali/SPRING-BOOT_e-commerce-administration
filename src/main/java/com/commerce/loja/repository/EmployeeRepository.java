package com.commerce.loja.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.commerce.loja.model.Employee;

public interface EmployeeRepository  extends JpaRepository<Employee, Long>{

	/*
	 * @Query(
	 * value="select * from administradores where email = :email and password = :password"
	 * , nativeQuery = true)
	 */

	Optional<Employee> findByEmail(String email);
}
