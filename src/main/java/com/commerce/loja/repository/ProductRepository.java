package com.commerce.loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commerce.loja.model.Product;

public interface ProductRepository  extends JpaRepository<Product, Long>{

}
