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

import com.commerce.loja.model.Permissions;
import com.commerce.loja.repository.PermissionsRepository;
import com.commerce.loja.repository.RoleRepository;
import com.commerce.loja.repository.EmployeeRepository;

@Controller
public class PermissionsController {
	
	@Autowired
	private PermissionsRepository permissionsRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@GetMapping("/administrativo/permissoes/cadastrar")
	public ModelAndView register(Permissions permissions) {
		ModelAndView mv = new ModelAndView("administrative/permissions/registrationOfPermissions");
		mv.addObject("permissions", permissions);
		mv.addObject("listOfEmployees", employeeRepository.findAll());
		mv.addObject("listOfRole", roleRepository.findAll());
		return mv;
	}
	
	
	@PostMapping("/administrativo/permissoes/salvar")
	public ModelAndView save(@Validated Permissions permissions, BindingResult result) {
		if(result.hasErrors()) {
			return register(permissions);
		}
		permissionsRepository.saveAndFlush(permissions);
		return register(new Permissions());
	}
	
	
	@GetMapping("/administrativo/permissoes/listar")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("administrative/permissions/listPermissions");
		mv.addObject("listOfRole", permissionsRepository.findAll());
		return mv;
	}
	
	
	@GetMapping("/administrativo/permissoes/editar/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		Optional<Permissions> permissions = permissionsRepository.findById(id);
		return register(permissions.get());
	}
	
	@GetMapping("/administrativo/permissoes/remover/{id}")
	public String delete(@PathVariable("id") Long id) {
		Optional<Permissions> permissions = permissionsRepository.findById(id);
		permissionsRepository.delete(permissions.get());
		return "redirect:/administrativo/permissoes/listar";
	}
	

}
