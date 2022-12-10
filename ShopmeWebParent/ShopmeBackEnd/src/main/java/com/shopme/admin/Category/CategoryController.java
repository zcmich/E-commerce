package com.shopme.admin.Category;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.user.CategoryNotFoundException;
import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/categories")
    public String listAllCategories(Model model){
        List<Category> listCategories = categoryService.listAll();
        model.addAttribute("listCategories",listCategories);
        return "categories/categories";
    }

    @GetMapping("/categories/new")
    public String newCategory(Model model){

        List<Category> listCategories = categoryService.listCategoriesUsedInForm();
        model.addAttribute("listCategories",listCategories);

        model.addAttribute("pageTitle","Create new category");
        model.addAttribute("category", new Category());
        return "categories/categories_form";


    }


    @PostMapping("/categories/save")
    public String save(Category category, RedirectAttributes redirectAttributes, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        category.setImage(fileName);

        Category savedCategory = categoryService.save(category);
        String uploadDir = "./category-images/" + savedCategory.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);


        redirectAttributes.addFlashAttribute("message", "Category "+ category.getName() + " was successfully created!");

        return "redirect:/categories";
    }

    @GetMapping("/category/edit/{id}")
    public String editCategory(@PathVariable(name = "id")Integer id, Model model, RedirectAttributes redirectAttributes) throws CategoryNotFoundException {

        try {
            Category category = categoryService.getCategoryById(id);
            List<Category> listCategories = categoryService.listCategoriesUsedInForm();

            model.addAttribute("listCategories",listCategories);
            model.addAttribute("category", category);
            model.addAttribute("pageTitle","Edit Category (ID: " + id + ")");

            return "categories/categories_form";

        }catch (CategoryNotFoundException ex){

            redirectAttributes.addFlashAttribute("message", ex.getMessage());

            return "redirect:/categories";
        }



    }


}
