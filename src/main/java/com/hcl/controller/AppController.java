package com.hcl.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hcl.model.Cart;
import com.hcl.model.CartItem;
import com.hcl.model.Product;
import com.hcl.repository.ProductFilterObject;
import com.hcl.service.ProductService;

@Controller
public class AppController {
	@Autowired
	private ProductService productService;

	@Autowired
	private Cart cart;

	@RequestMapping("/admin")
	public String viewHomePageAdmin(Model model) {
		List<Product> listProducts = productService.listAll();
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("filter", new ProductFilterObject());
		model.addAttribute("endpoint", "/filter_index");
		model.addAttribute("clear", "/admin");
		return "index";
	}

	@GetMapping("/filter_index")
	public String filterProducts(@ModelAttribute ProductFilterObject filter, Model model) {
		List<Product> filteredProducts = productService.listFiltered(filter);
		model.addAttribute("listProducts", filteredProducts);
		model.addAttribute("filter", filter);
		model.addAttribute("endpoint", "/filter_index");
		model.addAttribute("clear", "/admin");
		return "index";
	}
	
	@RequestMapping("/user")
	public String viewHomePageUser(Model model) {
		List<CartItem> listCart = cart.listAll();
		float subtotal = cart.getSubtotal();
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

	@RequestMapping(value = "/cart_save", method = RequestMethod.GET)
	public String returnToCart() {
		return "redirect:/user";
	}

	@RequestMapping(value = "/cart_save", method = RequestMethod.POST)
	public String saveCart(@ModelAttribute("cart") CartItem item) {
		cart.updateItemQuantity(item);
		return "redirect:/user";
	}

	@RequestMapping(value = "/product_save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") Product product,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
		productService.save(product, multipartFile);

		return "redirect:/admin";
	}

	@RequestMapping(value = "/finish_edit", method = RequestMethod.POST)
	public String editProduct(@ModelAttribute("product") Product product,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
		productService.edit(product, multipartFile);
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
		model.addAttribute("filter", new ProductFilterObject());
		model.addAttribute("endpoint", "/filter_browse");
		model.addAttribute("clear", "/browse");
		return "query_product";
	}

	@GetMapping("/filter_browse")
	public String filterProductBrowse(@ModelAttribute ProductFilterObject filter, Model model) {
		List<Product> filteredProducts = productService.listFiltered(filter);
		model.addAttribute("listProducts", filteredProducts);
		model.addAttribute("filter", filter);
		model.addAttribute("endpoint", "/filter_browse");
		model.addAttribute("clear", "/browse");
		return "query_product";
	}

	@RequestMapping("/tinker/{id}")
	public ModelAndView userTinker(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("edit_quantity");

		CartItem item = cart.getItemById(id);
		mav.addObject("item", item);

		return mav;
	}

	@RequestMapping("/discard/{id}")
	public String discardProduct(@PathVariable(name = "id") Long id) {
		cart.deleteItemById(id);

		return "redirect:/user";
	}

	@RequestMapping(value = "/add/{id}")
	public String addToCart(@PathVariable(name = "id") Long id) {
		Product product = productService.get(id);
		CartItem item = new CartItem();
		item.setId(id);
		item.setName(product.getName());
		item.setPrice(product.getPrice());
		item.setImageFileName(product.getImageName());
		item.setQty(1);
		cart.addItem(item);
		return "redirect:/user";
	}
}
