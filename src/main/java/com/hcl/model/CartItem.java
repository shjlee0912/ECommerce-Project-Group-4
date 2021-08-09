package com.hcl.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItem {
	
	private Long id;
	private String name;
	private float price;
	private int qty;
	private float total;

	public float getTotal() {
		setTotal(this.price * this.qty);
		return total;
	}

}