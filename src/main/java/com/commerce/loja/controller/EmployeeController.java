package com.commerce.loja.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.commerce.loja.model.Employee;
import com.commerce.loja.repository.EmployeeRepository;

@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/administrativo/funcionarios/cadastrar")
	public ModelAndView register(Employee employee) {
		ModelAndView mv = new ModelAndView("administrative/employee/cadastro");
		mv.addObject("employee", employee);
		return mv;
	}
	
	
	@PostMapping("/administrativo/funcionarios/salvar")
	public ModelAndView save(@Validated Employee employee, BindingResult result) {
		if(result.hasErrors()) {
			return register(employee);
		}
		employeeRepository.saveAndFlush(employee);
		return register(new Employee());
	}
	
	
	@GetMapping("/administrativo/funcionarios/listar")
	public ModelAndView employeeList() {
		ModelAndView mv = new ModelAndView("administrative/employee/lista");
		mv.addObject("listOfEmployees", employeeRepository.findAll());
		return mv;
	}
	
	
	@GetMapping("/administrativo/funcionarios/editar/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		return register(employee.get());
	}
	

}
