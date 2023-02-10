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

import com.commerce.loja.model.State;
import com.commerce.loja.repository.StateRepository;

@Controller
public class StateController {
	
	@Autowired
	private StateRepository stateRepository;

	@GetMapping("/administrativo/estados/cadastrar")
	public ModelAndView register(State state) {
		ModelAndView mv = new ModelAndView("administrative/states/registrationState");
		mv.addObject("state", state);
		return mv;
	}
	
	
	@PostMapping("/administrativo/estados/salvar")
	public ModelAndView save(@Validated State state, BindingResult result) {
		if(result.hasErrors()) {
			return register(state);
		}
		stateRepository.saveAndFlush(state);
		return register(new State());
	}
	
	
	@GetMapping("/administrativo/estados/listar")
	public ModelAndView estateList() {
		ModelAndView mv = new ModelAndView("administrative/states/listState");
		mv.addObject("listOfStates", stateRepository.findAll());
		return mv;
	}
	
	
	@GetMapping("/administrativo/estados/editar/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		Optional<State> state = stateRepository.findById(id);
		return register(state.get());
	}
	
	@GetMapping("/administrativo/estados/remover/{id}")
	public String deleteState(@PathVariable("id") Long id) {
		Optional<State> state = stateRepository.findById(id);
		stateRepository.delete(state.get());
		return "redirect:/administrativo/estados/listar";
	}
	

}
