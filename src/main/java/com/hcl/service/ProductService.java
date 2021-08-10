package com.hcl.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hcl.model.Product;
import com.hcl.repository.ProductFilterObject;
import com.hcl.repository.ProductRepository;
import com.hcl.util.FileUploadUtil;

@Service
public class ProductService {
	@Autowired
	private ProductRepository repo;
	
	public List<Product> listAll() {		
		return repo.findAll();
	}
	
	public List<Product> listFiltered(ProductFilterObject filter) {
		if(filter==null) {
			return listAll();
		}
		return repo.getFilteredProducts(filter);
	}
	
	public void save(@ModelAttribute("product") Product product, @RequestParam("image") MultipartFile multipartFile) throws IOException {
		String fileName;
		if(multipartFile.isEmpty()) {
			fileName="default.png";
		} else {
			fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			String savingDir = "src/main/resources/static/image/";
			FileUploadUtil.saveFile(savingDir, fileName, multipartFile);
		}
		product.setImageName(fileName);
		repo.save(product);
	}
	
	public void edit(@ModelAttribute("product") Product product, @RequestParam("image") MultipartFile multipartFile) throws IOException{
		String originalFileName;
		Optional<Product> prd = repo.findById(product.getId());
		if(prd.isPresent()) {
			originalFileName = prd.get().getImageName();
		} else {
			originalFileName = "default.png";
		}
		String savingDir = "src/main/resources/static/image/";
		if(!multipartFile.isEmpty() && originalFileName!=null) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			if(!originalFileName.equals("default.png")) {
				FileUploadUtil.deleteFile(savingDir+originalFileName);
			}
			FileUploadUtil.saveFile(savingDir, fileName, multipartFile);
			product.setImageName(fileName);
		} else {
			product.setImageName(originalFileName);
		}
		repo.save(product);
	}
	
//	public void save(@ModelAttribute("product")Product product) {
//		repo.save(product);
//	}
	
	public Product get(Long id) {
		return repo.findById(id).get();
	}
	
	public void delete(Long id) {
		Product prd = repo.findById(id).get();
		String fileDir = "src/main/resources/static/image/" + prd.getImageName();
		if(!prd.getImageName().equals("default.png")) {
			FileUploadUtil.deleteFile(fileDir);
		}
		repo.deleteById(id);
	}
}
