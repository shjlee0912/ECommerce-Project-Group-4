package com.hcl.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;

import com.hcl.model.Product;

public class ProductRepositoryFilteredImpl implements ProductRepositoryFiltered {

	@Autowired
	EntityManager em;

	@Override
	public List<Product> getFilteredProducts(ProductFilterObject filter) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Product> cr = cb.createQuery(Product.class);
		Root<Product> product = cr.from(Product.class);
		if (filter!=null) {
			List<Predicate> predicates = new ArrayList<>();
			if (filter.isUsingMinPrice()) {
				predicates.add(cb.ge(product.get("price"), filter.getMinPrice()));
			}
			if (filter.isUsingMaxPrice()) {
				predicates.add(cb.le(product.get("price"), filter.getMaxPrice()));
			}
			if (filter.isUsingName()) {
				predicates.add(cb.like(product.get("name"), "%" + filter.getNameIncludes() + "%"));
			}
			if (filter.isUsingCategory()) {
				// predicates.add(cb.equal(product.get("category"), filter.getCategory()));
			}
			cr.where(predicates.toArray(new Predicate[predicates.size()]));
		}
		return em.createQuery(cr).getResultList();
	}

}
