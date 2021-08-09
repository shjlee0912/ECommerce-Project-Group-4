package com.hcl.model;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@Component
@SessionScope
@Data
public class Cart {

	private List<CartItem> items;

	public Cart() {
		items = new LinkedList<CartItem>();
	}

	public List<CartItem> listAll() {
		return new LinkedList(items);
	}

	public float getSubtotal() {
		return items.stream().map(i -> (float) i.getTotal()).reduce(0f, (t1, t2) -> t1 + t2);
	}

	public void addItem(CartItem item) {
		items.add(item);
	}

	public void updateItemQuantity(CartItem item) {
		CartItem toUpdate = getItemById(item.getId());
		toUpdate.setQty(item.getQty());
	}

	public CartItem getItemById(long id) {
		return items.stream().filter(i -> i.getId() == id).findAny().orElse(null);
	}

	public void deleteItemById(long id) {
		for (ListIterator<CartItem> itr = items.listIterator(); itr.hasNext();) {
			CartItem next = itr.next();
			if (next.getId() == id) {
				itr.remove();
				break;
			}
		}
	}
}
