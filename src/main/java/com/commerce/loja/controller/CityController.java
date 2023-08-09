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

import com.commerce.loja.model.City;
import com.commerce.loja.repository.CityRepository;
import com.commerce.loja.repository.StateRepository;

@Controller
public class CityController {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private StateRepository stateRepository;

	@GetMapping("/administrativo/cidades/cadastrar")
	public ModelAndView register(City city) {
		ModelAndView mv = new ModelAndView("administrative/cities/registrationCity");
		mv.addObject("city", city);
		mv.addObject("listOfStates", stateRepository.findAll());
		return mv;
	}
	
	
	@PostMapping("/administrativo/cidades/salvar")
	public ModelAndView save(@Validated City City, BindingResult result) {
		if(result.hasErrors()) {
			return register(City);
		}
		cityRepository.saveAndFlush(City);
		return register(new City());
	}
	
	
	@GetMapping("/administrativo/cidades/listar")
	public ModelAndView cityList() {
		ModelAndView mv = new ModelAndView("administrative/cities/listCity");
		mv.addObject("listOfCities", cityRepository.findAll());
		return mv;
	}
	
	
	@GetMapping("/administrativo/cidades/editar/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		Optional<City> city = cityRepository.findById(id);
		return register(city.get());
	}
	
	@GetMapping("/administrativo/cidades/remover/{id}")
	public String deleteCity(@PathVariable("id") Long id) {
		Optional<City> city = cityRepository.findById(id);
		cityRepository.delete(city.get());
		return "redirect:/administrativo/cidades/listar";
	}
	
//	@GetMapping("/login")
//	public String log() {
//		return "administrative/login/login";
//	}
	

}
