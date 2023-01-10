package com.shopme.admin.brand;

import com.shopme.admin.Category.CategoryService;
import com.shopme.admin.FileUploadUtil;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
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


    @PostMapping("/brands/save")
    public String saveBrand(Brand brand, @RequestParam("image") MultipartFile multipartFile, RedirectAttributes redirectAttributes) throws IOException {

        if(!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            brand.setLogo(fileName);
            Brand savedBrand = brandService.save(brand);
            String uploadDir = "brand-logos/" + savedBrand.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }else{
            if (brand.getLogo().isEmpty()) brand.setLogo(null);
            brandService.save(brand);
        }

        redirectAttributes.addFlashAttribute("message", "Brand "+ brand.getName()+ " "+" has been saved successfully");

        return "redirect:brands/brands";
    }

}
