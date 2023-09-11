package com.fome.projectfome.service;


import java.util.List;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.fome.projectfome.dao.CategoryRepository;
import com.fome.projectfome.model.Category;


@Transactional
@Service
public class CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    //criando um produto
    public Category createCategory(Category category){

        categoryRepository.save(category);

        return category;
    }


    // achar um unico produto
    public Category findById(int id){

       return categoryRepository.findById(id);

    }

    public Category findById2(int id){

        return categoryRepository.findById2(id);

    }

    // mostrar todos os produtos
    public List<Category> findAll(){

        return categoryRepository.findAll();
    }

    // fazebndo update em um produto
    public Category updateCategory(Category category){

        Category newCategory = categoryRepository.findById(category.getId());
        newCategory.setName(category.getName());

        categoryRepository.save(newCategory);

        return newCategory;

    }

    // delete um produto
    public String deleteCategory(int id){

        categoryRepository.delete(categoryRepository.findById(id)); 

        return "product deleted successfully";
    }

   
}