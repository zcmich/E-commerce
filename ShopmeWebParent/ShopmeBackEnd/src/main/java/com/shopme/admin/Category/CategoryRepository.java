package com.shopme.admin.Category;

import com.shopme.common.entity.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {
    List<Category> findAll();
    @Query("SELECT cat from Category cat where cat.parent.id is NULL ")
   public List<Category> findRootCategories(Sort sort);
    Category findCategoriesById(int id);
    Category findByName(String name);
    Category findByAlias(String alias);
    Long countById(Integer id);

    @Query("UPDATE Category c SET c.enabled = ?2 WHERE c.id = ?1")
    @Modifying
    void updateCategoryEnabledStatus(Integer id, boolean enabled);

}
