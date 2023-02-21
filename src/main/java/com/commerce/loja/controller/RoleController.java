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

import com.commerce.loja.model.Role;
import com.commerce.loja.repository.RoleRepository;

@Controller
public class RoleController {
	
	@Autowired
	private RoleRepository roleRepository;

	@GetMapping("/administrativo/papeis/cadastrar")
	public ModelAndView register(Role role) {
		ModelAndView mv = new ModelAndView("administrative/role/registrationRole");
		mv.addObject("role", role);
		return mv;
	}
	
	
	@PostMapping("/administrativo/papeis/salvar")
	public ModelAndView save(@Validated Role role, BindingResult result) {
		if(result.hasErrors()) {
			return register(role);
		}
		roleRepository.saveAndFlush(role);
		return register(new Role());
	}
	
	
	@GetMapping("/administrativo/papeis/listar")
	public ModelAndView roleList() {
		ModelAndView mv = new ModelAndView("administrative/role/listRole");
		mv.addObject("listOfRoles", roleRepository.findAll());
		return mv;
	}
	
	
	@GetMapping("/administrativo/papeis/editar/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		Optional<Role> role = roleRepository.findById(id);
		return register(role.get());
	}
	
	@GetMapping("/administrativo/papeis/remover/{id}")
	public String deleteRole(@PathVariable("id") Long id) {
		Optional<Role> role = roleRepository.findById(id);
		roleRepository.delete(role.get());
		return "redirect:/administrativo/papeis/listar";
	}
	

}
