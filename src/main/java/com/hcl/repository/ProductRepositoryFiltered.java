package com.hcl.repository;

import java.util.List;

import com.hcl.model.Product;

public interface ProductRepositoryFiltered {

	public List<Product> getFilteredProducts(ProductFilterObject filter);
	
}
