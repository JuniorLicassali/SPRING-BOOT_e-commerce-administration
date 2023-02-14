package com.commerce.loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commerce.loja.model.ItemEntry;

public interface ItemEntryRepository  extends JpaRepository<ItemEntry, Long>{

}
