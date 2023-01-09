package com.shopme.admin.brand;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class BrandRepositoryTests {

    @Autowired
    BrandRepository brandRepository;


    @Test
    public void testCreateBrand1(){
        Category category = new Category(6);
        Brand brand = new Brand("Acer");
        brand.getCategories().add(category);


        Brand savedBrand = brandRepository.save(brand);

        assertThat(savedBrand.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateBrand2(){
        Category category_cellphones = new Category(4);
        Category category_tablets = new Category(7);

        Brand brand = new Brand("Apple");
        brand.getCategories().addAll(Arrays.asList(category_cellphones,category_tablets));


        Brand savedBrand = brandRepository.save(brand);

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateBrand3(){
        Category category_memory = new Category(29);
        Category category_internalHardDrives = new Category(24);

        Brand brand = new Brand("Samsung");
        brand.getCategories().addAll(Arrays.asList(category_memory,category_internalHardDrives));


        Brand savedBrand = brandRepository.save(brand);

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetAllBrands(){
        Iterable<Brand> allBrands = brandRepository.findAll();
        allBrands.forEach(System.out::println);
        assertThat(allBrands).isNotNull();
    }

    @Test
    public void getBrandById(){
      Brand brand = brandRepository.findById(1).get();
      assertThat(brand.getName()).isEqualTo("Acer");
    }

    @Test
    public void testUpdateBrand(){
        String newName = "Samsung Electronic";
        Brand samsung = brandRepository.findById(5).get();
        samsung.setName(newName);

        Brand savedBrand = brandRepository.save(samsung);
        assertThat(savedBrand.getName()).isEqualTo(newName);
    }

    @Test
    public void testDeleteBrand(){
        Integer brandId = 6;
       brandRepository.deleteById(brandId);

       Optional<Brand> brandFound = brandRepository.findById(brandId);
       assertThat(brandFound.isEmpty());
    }


}
