package com.hcl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hcl.model.Cart;
import com.hcl.model.Product;
import com.hcl.service.CartService;
import com.hcl.service.ProductService;

@Controller
public class AppController {
	@Autowired
	private ProductService productService;

	@Autowired
	private CartService cartService;

	@RequestMapping("/admin")
	public String viewHomePageAdmin(Model model) {
		List<Product> listProducts = productService.listAll();
		model.addAttribute("listProducts", listProducts);
		return "index";
	}

	@RequestMapping("/user")
	public String viewHomePageUser(Model model) {
		List<Cart> listCart = cartService.listAll();
		float subtotal = cartService.getSubtotal();
		model.addAttribute("listCart", listCart);
		model.addAttribute("subtotal", subtotal);
		return "userindex";
	}

	@RequestMapping("/new")
	public String showNewProductForm(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);

		return "new_product";
	}

	@RequestMapping("/order_complete")
	public String orderComplete() {
		return "thank_you";
	}
	
	@RequestMapping(value="/cart_save", method = RequestMethod.GET)
	public String returnToCart() {
		return "redirect:/user";
	}
	
	@RequestMapping(value = "/cart_save", method = RequestMethod.POST)
	public String saveCart(@ModelAttribute("cart") Cart cart) {
		cart.computeTotal();
		cartService.save(cart);

		return "redirect:/user";
	}

	@RequestMapping(value = "/product_save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") Product product) {
		productService.save(product);

		return "redirect:/admin";
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductForm(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("edit_product");

		Product product = productService.get(id);
		mav.addObject("product", product);

		return mav;
	}

	@RequestMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") Long id) {
		productService.delete(id);

		return "redirect:/admin";
	}

	@RequestMapping("/browse")
	public String viewProducts(Model model) {
		List<Product> listProducts = productService.listAll();
		model.addAttribute("listProducts", listProducts);
		return "query_product";
	}
	
	@RequestMapping("/tinker/{id}")
	public ModelAndView userTinker(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("edit_quantity");

		Cart cart = cartService.get(id);
		mav.addObject("cart", cart);

		return mav;
	}

	@RequestMapping("/discard/{id}")
	public String discardProduct(@PathVariable(name = "id") Long id) {
		cartService.discard(id);

		return "redirect:/user";
	}
	
	@RequestMapping(value = "/add/{id}")
	public String addToCart(@PathVariable(name = "id") Long id){
		Product product = productService.get(id);
		Cart cart = new Cart();
		cart.setId(id);
		cart.setName(product.getName());
		cart.setPrice(product.getPrice());
		cart.setQty(1);
		cart.computeTotal();
		cartService.save(cart);
		return "redirect:/user";
	}
}
