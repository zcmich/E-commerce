package com.shopme.admin.Category;

import com.shopme.common.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {
    List<Category> findAll();
    @Query("SELECT cat from Category cat where cat.parent.id is NULL ")
   public List<Category> findRootCategories();

    Category findCategoriesById(int id);
}
