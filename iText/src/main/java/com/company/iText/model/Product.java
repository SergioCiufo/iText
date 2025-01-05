package com.company.iText.model;

import java.util.Objects;

public class Product {
	private String description;
	private float quantity;
	private float price;
	private float vat;
	
	public Product() {
		super();
	}

	public Product(String description, float quantity, float price, float vat) {
		super();
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.vat = vat;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getVat() {
		return vat;
	}

	public void setVat(float vat) {
		this.vat = vat;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, price, quantity, vat);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(description, other.description)
				&& Float.floatToIntBits(price) == Float.floatToIntBits(other.price)
				&& Float.floatToIntBits(quantity) == Float.floatToIntBits(other.quantity)
				&& Float.floatToIntBits(vat) == Float.floatToIntBits(other.vat);
	}

	@Override
	public String toString() {
		return "Product [description=" + description + ", quantity=" + quantity + ", price=" + price + ", vat=" + vat
				+ "]";
	}
	
}
