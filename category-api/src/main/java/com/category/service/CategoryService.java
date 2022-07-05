package com.category.service;

import java.util.List;

import com.category.entity.Category;
import com.category.vo.CategoryVo;

public interface CategoryService {
	
	public Long insertCategory(CategoryVo vo);
	
	public List<Category> findCategory(String str);
	
	public Category updateCategory(CategoryVo vo, Long id);
	
	public void deleteCategory(Long id, Boolean isDeleteChild);
	
	public Category isExist(Long id);
	
}
