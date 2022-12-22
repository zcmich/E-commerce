package com.shopme.admin.Category;


import com.shopme.admin.user.CategoryNotFoundException;
import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;


@Service
@Transactional
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> listAll() {
        List<Category> rootCategories = categoryRepository.findRootCategories();

        return listHierarchicalCategories(rootCategories);
    }

    public Category getCategoryById(Integer id) throws CategoryNotFoundException {
         try{
             return categoryRepository.findCategoriesById(id);
         } catch (NoSuchElementException ex){
             throw new CategoryNotFoundException("could not find any category with Id"+ id);
         }

    }

    private List<Category> listHierarchicalCategories(List<Category> rootCategories){
        List<Category> hierarchicalCategories = new ArrayList<>();

        for(Category rootCategory : rootCategories){
            hierarchicalCategories.add(Category.copyFull(rootCategory));

            Set<Category> children = rootCategory.getChildren();

            for (Category subCategory : children){
                String name = "--" + subCategory.getName();
                hierarchicalCategories.add(Category.copyFull(subCategory, name));
                listSubHierarchicalCategories(hierarchicalCategories, subCategory, 1);
            }

        }
      return hierarchicalCategories;
    }

    private void listSubHierarchicalCategories(List<Category> hierarchicalCategories,Category parent, int subLevel){
        Set<Category> children = parent.getChildren();
        int newSubLevel = subLevel + 1;
        for (Category subCategory : children){
            String name = "";
            for (int i = 0; i < newSubLevel ; i++) {
                name += "--" ;
            }
            name+=subCategory.getName();
            hierarchicalCategories.add(Category.copyFull(subCategory, name));
            listSubCategoriesUsedInForm(hierarchicalCategories, subCategory, newSubLevel);
        }
    }


    public  Category save(Category category){
        return categoryRepository.save(category);
    }

    public List<Category> listCategoriesUsedInForm() {
        List<Category> listCategoriesUsedInForm = new ArrayList<>();
        List<Category> allCategoriesInDb = categoryRepository.findAll();

        for (Category category : allCategoriesInDb){
            if( category.getParent() == null ){

                listCategoriesUsedInForm.add(category);

                Set<Category> children = category.getChildren();

                for (Category subCategory : children){
                    String categoryName = "--" + subCategory.getName();
                    listCategoriesUsedInForm.add(Category.copyIdAndName(categoryName, subCategory.getId()));

                    listSubCategoriesUsedInForm( listCategoriesUsedInForm, subCategory, 1);
                }
            }

        }


        return listCategoriesUsedInForm;
    }




    private void listSubCategoriesUsedInForm(List<Category> listCategoriesUsedInForm, Category parent, int subLevel){
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();

        for (Category subCategory : children){
            String name = "";

            for (int i = 0; i < newSubLevel ; i++) {
                name += "--" ;
            }

            name+=subCategory.getName();

            listCategoriesUsedInForm.add(Category.copyIdAndName(name, subCategory.getId()));
            listSubCategoriesUsedInForm(listCategoriesUsedInForm, subCategory, newSubLevel);
        }
    }


    public String checkUnique(Integer id, String name, String alias){
        boolean isCreatingNew = (id == null || id == 0);

        Category categoryByName = categoryRepository.findByName(name);

        if (isCreatingNew) {
            if(categoryByName != null){
                return "DuplicateName";
            }else{
                Category categoryByAlias = categoryRepository.findByAlias(alias);
                if(categoryByAlias != null){
                    return "DuplicateAlias";
                }
            }
        }else{

            if(categoryByName != null && categoryByName.getId() != id){
                return "DuplicateName";
            }

            Category categoryByAlias = categoryRepository.findByAlias(alias);
            if(categoryByAlias != null && categoryByAlias.getId() != id ){
                return "DuplicateAlias";
            }
        }

        return "OK";
    }

}
