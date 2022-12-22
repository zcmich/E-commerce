package com.shopme.admin.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryRestController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/categories/check_unique")
    public String checkUnique(@Param("name") String name, @Param("alias") String alias,
                              @Param("id") Integer id){
        return  categoryService.checkUnique(id, name, alias);
    }



}
