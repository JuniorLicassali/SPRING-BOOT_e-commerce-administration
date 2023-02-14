package com.commerce.loja.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.commerce.loja.model.ItemEntry;
import com.commerce.loja.model.Product;
import com.commerce.loja.model.ProductEntry;
import com.commerce.loja.repository.EmployeeRepository;
import com.commerce.loja.repository.ItemEntryRepository;
import com.commerce.loja.repository.ProductEntryRepository;
import com.commerce.loja.repository.ProductRepository;

@Controller
public class ProductEntryController {
	
	List<ItemEntry> listOfItems = new ArrayList<ItemEntry>();
	
	@Autowired
	private ProductEntryRepository productEntryRepository;
	
	@Autowired
	private ItemEntryRepository itemEntryRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/administrativo/entrada/cadastrar")
	public ModelAndView register(ProductEntry entry, ItemEntry itemEntry) {
		ModelAndView mv = new ModelAndView("administrative/entry/registrationEntry");
		mv.addObject("entry", entry);
		mv.addObject("listOfItems", this.listOfItems);
		mv.addObject("itemEntry", itemEntry);
		mv.addObject("listOfEmployees", employeeRepository.findAll());
		mv.addObject("listOfProducts", productRepository.findAll());
		
		return mv;
	}
	
	
	@PostMapping("/administrativo/entrada/salvar")
	public ModelAndView save(String action, ProductEntry entry, ItemEntry itemEntry) {
		if(action.equals("itens")) {
			this.listOfItems.add(itemEntry);
		}else if(action.equals("save")) {
			productEntryRepository.saveAndFlush(entry);
			for(ItemEntry item : listOfItems) {
				item.setProductEntry(entry);
				itemEntryRepository.saveAndFlush(item);
				Optional<Product> prod = productRepository.findById(item.getProduct().getId());
				Product product = prod.get();
				product.setInventoryQuantity(product.getInventoryQuantity() + item.getAmount());
				product.setSaleValue(item.getSaleValue());
				productRepository.saveAndFlush(product);
				this.listOfItems = new ArrayList<>();
				}
			return register(new ProductEntry(), new ItemEntry());
		}
		return register(entry, new ItemEntry());
	}
	
	
	/*
	 * @GetMapping("/administrativo/estados/listar") public ModelAndView
	 * estateList() { ModelAndView mv = new
	 * ModelAndView("administrative/states/listState"); mv.addObject("listOfStates",
	 * stateRepository.findAll()); return mv; }
	 */
	
	
	/*
	 * @GetMapping("/administrativo/estados/editar/{id}") public ModelAndView
	 * edit(@PathVariable("id") Long id) { Optional<State> state =
	 * stateRepository.findById(id); return register(state.get()); }
	 */
	
	/*
	 * @GetMapping("/administrativo/estados/remover/{id}") public String
	 * deleteState(@PathVariable("id") Long id) { Optional<State> state =
	 * stateRepository.findById(id); stateRepository.delete(state.get()); return
	 * "redirect:/administrativo/estados/listar"; }
	 */
	

}
