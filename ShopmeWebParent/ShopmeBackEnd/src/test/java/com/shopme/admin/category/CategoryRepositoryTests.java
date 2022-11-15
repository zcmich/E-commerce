package com.shopme.admin.category;

import com.shopme.admin.Category.CategoryRepository;
import com.shopme.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CategoryRepositoryTests {
    @Autowired
    private CategoryRepository categoryRepository;


    @Test
    public void testCreateRootCategory(){
        Category category = new Category("Electronics", "1", "img.png");
       Category savedCategory = categoryRepository.save(category);
       assertThat(savedCategory.getId()).isGreaterThan(0);
    }

}
