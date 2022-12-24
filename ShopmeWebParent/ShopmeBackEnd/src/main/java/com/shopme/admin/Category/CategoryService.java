package com.shopme.admin.Category;


import com.shopme.admin.user.CategoryNotFoundException;
import com.shopme.admin.user.UserNotFoundException;
import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


@Service
@Transactional
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> listAll(String sortDir) {
        Sort sort = Sort.by("name");

        if (sortDir.equals("asc")) {
            sort = sort.ascending();
        } else if (sortDir.equals("desc")) {
            sort = sort.descending();
        }

        List<Category> rootCategories = categoryRepository.findRootCategories(sort);

        return listHierarchicalCategories(rootCategories, sortDir);
    }

    public Category getCategoryById(Integer id) throws CategoryNotFoundException {
        try {
            return categoryRepository.findCategoriesById(id);
        } catch (NoSuchElementException ex) {
            throw new CategoryNotFoundException("could not find any category with Id" + id);
        }

    }

    private List<Category> listHierarchicalCategories(List<Category> rootCategories, String sortDir) {
        List<Category> hierarchicalCategories = new ArrayList<>();

        for (Category rootCategory : rootCategories) {
            hierarchicalCategories.add(Category.copyFull(rootCategory));

            Set<Category> children = sortSubCategories(rootCategory.getChildren(), sortDir);

            for (Category subCategory : children) {
                String name = "--" + subCategory.getName();
                hierarchicalCategories.add(Category.copyFull(subCategory, name));
                listSubHierarchicalCategories(hierarchicalCategories, subCategory, 1, sortDir);
            }

        }
        return hierarchicalCategories;
    }

    private void listSubHierarchicalCategories(List<Category> hierarchicalCategories,
                                               Category parent, int subLevel, String sortDir) {
        Set<Category> children = sortSubCategories(parent.getChildren(), sortDir);
        int newSubLevel = subLevel + 1;
        for (Category subCategory : children) {
            String name = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }
            name += subCategory.getName();
            hierarchicalCategories.add(Category.copyFull(subCategory, name));
            listSubCategoriesUsedInForm(hierarchicalCategories, subCategory, newSubLevel);
        }
    }


    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> listCategoriesUsedInForm() {
        List<Category> listCategoriesUsedInForm = new ArrayList<>();
        List<Category> allCategoriesInDb = categoryRepository.findRootCategories(Sort.by("name"));

        for (Category category : allCategoriesInDb) {
            if (category.getParent() == null) {

                listCategoriesUsedInForm.add(category);

                Set<Category> children = sortSubCategories(category.getChildren());

                for (Category subCategory : children) {
                    String categoryName = "--" + subCategory.getName();
                    listCategoriesUsedInForm.add(Category.copyIdAndName(categoryName, subCategory.getId()));

                    listSubCategoriesUsedInForm(listCategoriesUsedInForm, subCategory, 1);
                }
            }

        }


        return listCategoriesUsedInForm;
    }


    private void listSubCategoriesUsedInForm(List<Category> listCategoriesUsedInForm, Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = sortSubCategories(parent.getChildren());

        for (Category subCategory : children) {
            String name = "";

            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }

            name += subCategory.getName();

            listCategoriesUsedInForm.add(Category.copyIdAndName(name, subCategory.getId()));
            listSubCategoriesUsedInForm(listCategoriesUsedInForm, subCategory, newSubLevel);
        }
    }


    public String checkUnique(Integer id, String name, String alias) {
        boolean isCreatingNew = (id == null || id == 0);

        Category categoryByName = categoryRepository.findByName(name);

        if (isCreatingNew) {
            if (categoryByName != null) {
                return "DuplicateName";
            } else {
                Category categoryByAlias = categoryRepository.findByAlias(alias);
                if (categoryByAlias != null) {
                    return "DuplicateAlias";
                }
            }
        } else {

            if (categoryByName != null && categoryByName.getId() != id) {
                return "DuplicateName";
            }

            Category categoryByAlias = categoryRepository.findByAlias(alias);
            if (categoryByAlias != null && categoryByAlias.getId() != id) {
                return "DuplicateAlias";
            }
        }

        return "OK";
    }

    private SortedSet<Category> sortSubCategories(Set<Category> children) {
      return sortSubCategories(children, "asc");
    }

    private SortedSet<Category> sortSubCategories(Set<Category> children, String sortDir) {
        SortedSet<Category> sortedChildren = new TreeSet<>(new Comparator<Category>() {
            @Override
            public int compare(Category cat1, Category cat2) {
                if(sortDir.equals("asc")){
                    return cat1.getName().compareTo(cat2.getName());
                }else {
                    return cat2.getName().compareTo(cat1.getName());

                }

            }
        });
        sortedChildren.addAll(children);
        return sortedChildren;
    }


    public void updateCategoriesEnabledStatus(Integer id, boolean enabled) {
//        Category categoryFound = categoryRepository.findCategoriesById(id);
//        categoryFound.setEnabled(enabled);
//        categoryRepository.save(categoryFound);
        categoryRepository.updateCategoryEnabledStatus(id, enabled);
    }

    public void delete(Integer id) throws CategoryNotFoundException {
        Long countById = categoryRepository.countById(id);
        if (countById == null || countById == 0){
            throw new CategoryNotFoundException("Could not find Category with ID" + id);
        }
        categoryRepository.deleteById(id);

    }


}
