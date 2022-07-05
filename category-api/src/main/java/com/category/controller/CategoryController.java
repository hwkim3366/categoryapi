package com.category.controller;

import com.category.entity.Category;
import com.category.service.CategoryService;
import com.category.vo.CategoryVo;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class CategoryController {
	
	private CategoryService categoryService;
	
    public CategoryController(CategoryService categoryService) {
    	
        this.categoryService = categoryService;
    }
    
    //insert
    @PostMapping("/category")
    public void insertCategory(@RequestBody @Valid CategoryVo vo) {
    	
        categoryService.insertCategory(vo);
    }
    
    //select
    @GetMapping("/category")
    public List<Category> findCategory(@Nullable String str) {
    	
        List<Category> categories =categoryService.findCategory(str);
        
        return categories;
    }
    
    //update
    @PutMapping("/category/{id}")
    public void updateCategory(@RequestBody @Valid CategoryVo vo, @PathVariable("id") Long id) {
    	
        categoryService.updateCategory(vo, id);
    }
    
    //delete
    @DeleteMapping("/category/{id}")
    public void deleteCategory(@PathVariable("id") Long id, @RequestParam("isDeleteChild") Boolean isDeleteChild ) {
    	
        categoryService.deleteCategory( id, isDeleteChild);
    }
}
