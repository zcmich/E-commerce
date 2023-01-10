package com.shopme.admin.brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrandRestController {
    @Autowired
    private BrandService brandService;


    @PostMapping("/brands/check_name")
    public String checkBrandNameEmail(@Param("brandName") String brandName, @Param("brandID") Integer brandID){
        brandService.isBrandNameUnique(brandID, brandName);


        return null;
    }


}
