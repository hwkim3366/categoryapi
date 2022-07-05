package com.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.category.entity.Category;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> , JpaSpecificationExecutor<Category> {

    List<Category> findAllByParentId(Long uppperCategoryId);
}
