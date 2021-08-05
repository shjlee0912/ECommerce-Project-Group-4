package net.codejava;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

	protected Cart() {
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setTotal() {
		this.total = this.price * (float)this.qty;
	}

	public float getPrice() {
		return price;
	}
	
	public float getTotal() {
		return total;
	}
	
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	public int getQty() {
		return qty;
	}

	public void setPrice(float price) {
		this.price = price;
	}


	public float getSubtotal() {
		return subtotal;
	}


	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

}