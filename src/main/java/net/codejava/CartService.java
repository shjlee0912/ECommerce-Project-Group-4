package net.codejava;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
	@Autowired
	private CartRepository repo;
	
	public List<Cart> listAll() {		
		return repo.findAll();
	}
	
	public void save(Cart cart) {
		repo.save(cart);
	}
	
	public Cart get(Long id) {
		return repo.findById(id).get();
	}
	
	public void discard(Long id) {
		repo.deleteById(id);
	}
	public float getSubtotal() {
		float sum = 0;
		List<Cart> lista = repo.findAll();
		for(Cart c : lista) {
			sum += c.getQty() * c.getPrice();
		}
		return sum;
	}
}
