package com.shopme.admin.Category;

import com.shopme.common.entity.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {
}
