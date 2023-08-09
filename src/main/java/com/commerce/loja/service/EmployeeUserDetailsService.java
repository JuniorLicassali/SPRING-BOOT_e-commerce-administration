package com.commerce.loja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.commerce.loja.model.Employee;
import com.commerce.loja.model.EmployeeUserDetailsImpl;
import com.commerce.loja.repository.EmployeeRepository;

@Service
public class EmployeeUserDetailsService implements UserDetailsService{

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Employee employee = employeeRepository.findByEmail(email)
				.orElseThrow( () -> new UsernameNotFoundException("Usuário não encontrado na base de dados") );
		return new EmployeeUserDetailsImpl(employee);
	}

}
