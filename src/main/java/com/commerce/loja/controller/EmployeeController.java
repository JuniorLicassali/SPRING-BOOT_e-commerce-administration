package com.commerce.loja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
		ModelAndView mv = new ModelAndView("administrativo/funcionarios/cadastro");
		mv.addObject("employee", employee);
		return mv;
	}
	
	@GetMapping("/administrativo/funcionarios/listar")
	public ModelAndView employeeList() {
		ModelAndView mv = new ModelAndView("administrativo/funcionarios/lista");
		mv.addObject("listOfEmployees", employeeRepository.findAll());
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
	
	@GetMapping("/listar")
	public String ver() {
		return "administrative/employee/lista";
	}
}
