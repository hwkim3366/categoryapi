package com.category.service.impl;

import com.category.config.exception.GlobalException;
import com.category.config.exception.GlobalExceptionEnumCode;
import com.category.entity.Category;
import com.category.repository.CategoryRepository;
import com.category.repository.CategorySpecification;
import com.category.service.CategoryService;
import com.category.vo.CategoryVo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{
    
	private CategoryRepository categoryRepository;
	
    public CategoryServiceImpl(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }
    
    //insert
    public Long insertCategory(CategoryVo vo) {
    	
    	Category newCategory  = new Category();
    	
    	//상위 카테고리 값 select
        Category category = this.isExist(vo.getParentId());
        
        //상위 카테고리를 지정 안했거나 상위카테고리가 존재하는 경우
        if (vo.getParentId() == null || category != null ) {
        	
        	newCategory.setCategoryName(vo.getCategoryName());
        	
        	newCategory.setParentId(vo.getParentId());
        	
        	return categoryRepository.save(newCategory).getId();
            
        } else {
        	
            throw new GlobalException(GlobalExceptionEnumCode.NOT_FOUND_PARENT_CATEGORY, GlobalExceptionEnumCode.NOT_FOUND_PARENT_CATEGORY.getMessage());
        }
    }
    
    //select
    public List<Category> findCategory(String str) {

        Specification<Category> specification = Specification.where(CategorySpecification.noParameter());

        if(str != null && !str.trim().isEmpty()) {
        	
        	specification = specification.and(CategorySpecification.likeSelect(str));
        }
        
        return categoryRepository.findAll(specification);
    }
    
    //update
    public Category updateCategory(CategoryVo vo, Long id) {
    	
        Category category =  this.isExist(id);
        
        if (category != null){

           //상위 카테고리를 수정 시
           if(vo.getParentId()!= null){
        	   
        	   //자기 자신의 하위 카테고리로는 수정 불가
               category.getChild().forEach(o->{
            	   
                    if (vo.getParentId().equals(o.getId())){
                    	
                        throw new GlobalException(GlobalExceptionEnumCode.CAN_NOT_UPDATE, GlobalExceptionEnumCode.CAN_NOT_UPDATE.getMessage());
                    }
               });
               
               category.setParentId(vo.getParentId());
           }

           category.setCategoryName(vo.getCategoryName());

           return categoryRepository.findById(category.getId()).get();

       } else {
           throw new GlobalException(GlobalExceptionEnumCode.NOT_FOUND_DATA, GlobalExceptionEnumCode.NOT_FOUND_DATA.getMessage());
       }
    }
    
    //delete
    public void deleteCategory(Long id, Boolean isDeleteChild) {
    	
        Category category = this.isExist(id);

        if (category != null){

            List<Category> categoryList = categoryRepository.findAllByParentId(id);
            
            if (categoryList.size() > 0) {
            	
                if (!isDeleteChild) {
                	
                	categoryList.forEach(o -> {o.setParentId(category.getParentId() == null ? null : category.getParentId()); categoryRepository.flush();});
                	
                } else {
                	
                	categoryList.forEach(o -> {deleteChild(o.getId());});
                }
            }

            categoryRepository.deleteById(category.getId());
            
        }else {
        	
            throw new GlobalException(GlobalExceptionEnumCode.NOT_FOUND_DATA, GlobalExceptionEnumCode.NOT_FOUND_DATA.getMessage());
        }
    }
    
    //delete All
    public void deleteChild(Long id) {
    	
    	categoryRepository.deleteById(id);
        
        List<Category> categoryList = categoryRepository.findAllByParentId(id);
        
        if (categoryList == null || categoryList.size() == 0) {
        	
            return;
        }
        
        categoryList.forEach(o -> {categoryRepository.delete(o); deleteChild(o.getId());});
    }

    //존재 여부 파악 목적
    public Category isExist(Long id) {
    	
        Optional<Category> categoryList = categoryRepository.findById(id);
        
        if (categoryList.isPresent()){
        	
            return categoryList.get();
            
        } else {
        	
            return null;
        }

    }

}
