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
import com.fome.projectfome.model.Product;
import com.fome.projectfome.service.ProductService;
import com.fome.projectfome.service.ValidationTokenService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ValidationTokenService validationToken;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/create")
    public Product createProductCtrl(@RequestBody Product product, HttpServletRequest tokenAuth){
        
        String userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);

        if(userId.equals("Permisson denied")){
        
            throw new HandlerException("Permisson denied");
        
        }

        return productService.creatProduct(product);
        
    }

    @GetMapping(value = "/find-by-id/{id}")
    public Product findByIdCtrl(@PathVariable int id){

        return productService.findById(id);
        
    }


    @GetMapping(value = "/find-all")
    public List<Product> findAllProductCtrl(){

        return productService.findAll();
        
    }

    @DeleteMapping(value = "/delete/{id}")
    public ApiResponseDTO deleteProductCtrl(@PathVariable int id, HttpServletRequest tokenAuth) {

        String userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);

        if(userId.equals("Permisson denied")){
        
            throw new HandlerException("Permisson denied");
        
        }

        return new ApiResponseDTO(true, productService.deleteProduct(id));
        
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping(path = "/update")
    public Product  updateProductCtrl(@RequestBody Product product, HttpServletRequest tokenAuth){

        String userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);

        if(userId.equals("Permisson denied")){
        
            throw new HandlerException("Permisson denied");
        
        }

        return productService.updateProduct(product);
    }
   
}
