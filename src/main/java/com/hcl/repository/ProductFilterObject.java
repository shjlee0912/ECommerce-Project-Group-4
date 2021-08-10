package com.hcl.repository;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductFilterObject {

	private boolean usingMinPrice = false;
	private double minPrice;
	
	private boolean usingMaxPrice = false;
	private double maxPrice;
	
	private boolean usingName = false;
	private String nameIncludes = "";
	
	private boolean usingCategory = false;
	private String category = "";
	
	
}
