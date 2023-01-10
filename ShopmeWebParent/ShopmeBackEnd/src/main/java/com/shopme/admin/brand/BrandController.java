package com.shopme.admin.brand;

import com.shopme.admin.Category.CategoryService;
import com.shopme.common.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BrandController {
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;;

    @GetMapping("/brands")
    public String listAllBrands(Model model, RedirectAttributes redirectAttributes){

        List<Brand> listBrands = brandService.listAllBrands();

        model.addAttribute("listBrands", listBrands);
        return "brands/brands";
    }

    @GetMapping("/brands/new")
    public String newBrand(Model model){
      Brand brand = new Brand();
      List listCategories = categoryService.listCategoriesUsedInForm();

      model.addAttribute("listCategories",listCategories);
      model.addAttribute("brand", brand);
      model.addAttribute("pageTitle", "Create new Brand");

        return "brands/brand_form";
    }

}
