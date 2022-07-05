package com.category;

import com.category.config.exception.GlobalException;
import com.category.entity.Category;
import com.category.service.CategoryService;
import com.category.vo.CategoryVo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class CategoryTest {

    @Autowired
    CategoryService categoryService;
    
    @Test
    @Transactional
    void insertCategoryTest()  throws Exception{
    	
        //입력받은 상위 카테고리가 존재하는 경우 정상 등록 처리
        CategoryVo categoryVo = new CategoryVo();
        
        categoryVo.setCategoryName("steve");
        
        categoryVo.setParentId(1L);
        
        Long id = categoryService.insertCategory(categoryVo);
        
        Assertions.assertThat(id).isGreaterThan(0);

        
        //입력받은 상위 카테고리가 존재하지 않는 경우 등록 실패
        CategoryVo categoryVo2 = new CategoryVo();
        
        categoryVo2.setCategoryName("steve");
        
        categoryVo2.setParentId(99l);

        Assertions.assertThatThrownBy(() -> categoryService.insertCategory(categoryVo2)).isInstanceOf(GlobalException.class);

    }
    
    @Test
    @Transactional
    void findCategoryTest() {
    	
        //전체 카테고리 조회
        String str = "";
        
        List<Category> categoryList = categoryService.findCategory(str);
        
        Assertions.assertThat(categoryList.size()).isGreaterThan(0);

        
        //존재하는 특정 카테고리 조회
        str = "elsa";
        
        categoryList = categoryService.findCategory(str);
        
        Assertions.assertThat(categoryList.size()).isGreaterThan(0);

        
        //존재하지 않는 특정 카테고리 조회
        str = "나까무라";
        
        categoryList = categoryService.findCategory(str);
        
        Assertions.assertThat(categoryList.size()).isZero();

    }
    
    
    @Test
    @Transactional
    void updateCategoryTest() {
    	
    	CategoryVo vo = new CategoryVo();

        //정상 수정
        vo.setCategoryName("jordan");
        
        vo.setParentId(null);
        
        Category category = categoryService.updateCategory(vo, 2l);
        
        Assertions.assertThat(category.getCategoryName()).isEqualTo("jordan");
        
        
        //상위 카테고리를 하위 카테고리로 변경하려는 경우 실패
        vo.setCategoryName("jordan");
        
        vo.setParentId(6l);

        Assertions.assertThatThrownBy(() ->categoryService.updateCategory(vo, 2l)).isInstanceOf(GlobalException.class);
        
        
        //수정 대상 카테고리가 없는 경우 실패
        Assertions.assertThatThrownBy(() ->categoryService.updateCategory(vo, 1000l)).isInstanceOf(GlobalException.class);

    }

    @Test
    @Transactional
    void deleteCategoryTest() {

        //하위 카테고리까지 전체 삭제
        Boolean isDeleteChild = true;
        
        Long id = 1l;
        
        categoryService.deleteCategory(id, isDeleteChild);
        
        int size = categoryService.findCategory(null).size();
        
        Assertions.assertThat(size).isEqualTo(4);
        

        //지정 카테고리만 삭제
        isDeleteChild = false;
        
        id = 2l;
        
        categoryService.deleteCategory(id, isDeleteChild);
        
        size = categoryService.findCategory(null).size();
        
        Assertions.assertThat(size).isEqualTo(3);

        
        //삭제할 카테고리가 없는 경우
        Assertions.assertThatThrownBy(() ->categoryService.deleteCategory(1000l, false)).isInstanceOf(GlobalException.class);
    }
}
