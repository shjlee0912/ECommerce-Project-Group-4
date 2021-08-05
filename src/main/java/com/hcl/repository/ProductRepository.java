package com.hcl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
