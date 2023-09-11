package com.fome.projectfome.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fome.projectfome.dto.ApiResponseDTO;
import com.fome.projectfome.dto.Exception.HandlerException;
import com.fome.projectfome.dto.enums.EUserRole;
import com.fome.projectfome.model.Category;
import com.fome.projectfome.service.CategoryService;
import com.fome.projectfome.service.ValidationTokenService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    ValidationTokenService validationToken;

    @Autowired
    CategoryService categoryService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/create")
    public Category createCategoryCtrl(@RequestBody Category category, HttpServletRequest tokenAuth){
        
        String userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);

        if(userId.equals("Permisson denied")){
        
            throw new HandlerException("Permisson denied");
        
        }

        return categoryService.createCategory(category);
        
    }

    @GetMapping(value = "/find-by-id/{id}")
    public Category findByIdCtrl(@PathVariable int id){

        return categoryService.findById(id);
        
    }


    @GetMapping(value = "/find-all")
    public List<Category> findAllCategoryCtrl(){

        return categoryService.findAll();
        
    }

    @DeleteMapping(value = "/delete/{id}")
    public ApiResponseDTO deleteCategoryCtrl(@PathVariable int id, HttpServletRequest tokenAuth) {

        String userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);

        if(userId.equals("Permisson denied")){
        
            throw new HandlerException("Permisson denied");
        
        }

        return  new ApiResponseDTO(true,categoryService.deleteCategory(id));
        
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping(path = "/update")
    public Category  updateCategoryCtrl(@RequestBody Category category, HttpServletRequest tokenAuth){

         String userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);

        if(userId.equals("Permisson denied")){
        
            throw new HandlerException("Permisson denied");
        
        }

        return categoryService.updateCategory(category);
    }
   
}
