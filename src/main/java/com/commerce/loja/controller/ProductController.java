package com.commerce.loja.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.commerce.loja.model.Product;
import com.commerce.loja.repository.ProductRepository;

@Controller
public class ProductController {
	
	private static String imagePath = "C:\\Users\\BELOSOS\\Documents\\imagens\\";
	
	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/administrativo/produtos/cadastrar")
	public ModelAndView register(Product product) {
		ModelAndView mv = new ModelAndView("administrative/products/registrationProduct");
		mv.addObject("product", product);
		return mv;
	}
	
	
	@PostMapping("/administrativo/produtos/salvar")
	public ModelAndView save(@Validated Product product, BindingResult result, @RequestParam("file") MultipartFile archive) {
		if(result.hasErrors()) {
			return register(product);
		}
		
		productRepository.saveAndFlush(product);
		
		try {
			if(!archive.isEmpty()) {
				byte[] bytes = archive.getBytes();
				Path path = Paths.get(imagePath+String.valueOf(product.getId())+archive.getOriginalFilename());
				Files.write(path, bytes);
				
				product.setNameImage(String.valueOf(product.getId())+archive.getOriginalFilename());
				productRepository.saveAndFlush(product);
			}
		} catch (IOException e) {
			e.printStackTrace();		
			}
		return register(new Product());
	}
	
	
	@GetMapping("/administrativo/produtos/listar")
	public ModelAndView eproductList() {
		ModelAndView mv = new ModelAndView("administrative/products/listProduct");
		mv.addObject("listOfProducts", productRepository.findAll());
		return mv;
	}
	
	
	@GetMapping("/administrativo/produtos/editar/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		Optional<Product> product = productRepository.findById(id);
		return register(product.get());
	}
	
	@GetMapping("/administrativo/produtos/remover/{id}")
	public String deleteproduct(@PathVariable("id") Long id) {
		Optional<Product> product = productRepository.findById(id);
		productRepository.delete(product.get());
		return "redirect:/administrativo/produtos/listar";
	}
	
	@GetMapping("/administrativo/produtos/mostrar-imagem/{image}")
	@ResponseBody
	public byte[] returnImage(@PathVariable("image") String image) throws IOException {
		File imageFile = new File(imagePath+image);
		if(image !=null || image.trim().length()>0) {
			return Files.readAllBytes(imageFile.toPath());
		}
		return null;
	}
	

}
