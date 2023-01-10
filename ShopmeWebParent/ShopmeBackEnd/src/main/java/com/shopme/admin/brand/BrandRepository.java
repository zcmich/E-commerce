package com.shopme.admin.brand;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BrandRepository extends PagingAndSortingRepository<Brand, Integer> {

    Brand findBrandByName(String brandName);

}
