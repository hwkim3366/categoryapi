package com.category.repository;

import org.springframework.data.jpa.domain.Specification;

import com.category.entity.Category;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CategorySpecification {

    @SuppressWarnings("serial")
	public static Specification<Category> noParameter() {
    	
        return new Specification<Category>() {
        	
            @Override
            public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                return null;
            }
        };
    }

    @SuppressWarnings("serial")
	public static Specification<Category> likeSelect(String str) {
    	
        return new Specification<Category>() {
        	
            @Override
            public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            	
                return criteriaBuilder.like(root.get("categoryName"), "%" + str + "%");
            }
        };
    }
}
