package com.shopme.admin.brand;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private  BrandRepository brandRepository;
    public List<Brand> listAllBrands() {
        return (List<Brand>) brandRepository.findAll();
    }

    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    public  boolean isBrandNameUnique(Integer brandID, String brandName) {
           Brand brandByName = brandRepository.findBrandByName(brandName);

            if (brandByName == null) return true;

            boolean isCreatingNull = (brandID == null);

            if (isCreatingNull) {
                if (brandByName != null) return false;
            } else {
                if (brandByName.getId() != brandID) {
                    return false;
                }
            }
            return true;
        }



}
