package com.commerce.loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commerce.loja.model.ProductEntry;

public interface ProductEntryRepository  extends JpaRepository<ProductEntry, Long>{

}
