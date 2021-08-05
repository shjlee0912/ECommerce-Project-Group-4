package com.hcl.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="cart")
public class Cart {
	@Id
	private Long id;
	private String name;
	private float price;
	private int qty;
	private float total;
	private float subtotal;

	public void computeTotal() {
		setTotal(this.price * (float)this.qty);
	}

}