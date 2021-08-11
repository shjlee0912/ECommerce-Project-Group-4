package com.hcl.model;

public class CartItem {
	
	private int qty;
	private float total;
	private Product product;
	
	public CartItem() {
		this.product = new Product();
	}
	
	public Long getId() {
		return product.getId();
	}

	//setters are necessary for the product's fields so that CartItem works with form-backing objects
	public void setId(Long id) {
		product.setId(id);
	}

	public String getName() {
		return product.getName();
	}

	public void setName(String name) {
		product.setName(name);
	}

	public float getPrice() {
		return product.getPrice();
	}

	public void setPrice(float price) {
		product.setPrice(price);
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getImageFileName() {
		return product.getImageName();
	}

	public void setImageFileName(String imageFileName) {
		product.setImageName(imageFileName);
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getTotal() {
		setTotal(product.getPrice() * qty);
		return total;
	}

}