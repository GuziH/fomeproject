package com.fome.projectfome.service;

import java.util.List;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.fome.projectfome.dao.ProductRepository;
import com.fome.projectfome.model.Product;


@Transactional
@Service
public class ProductService{

    @Autowired
    ProductRepository productRepository;

    //criando um produto
    public Product creatProduct(Product product){

        productRepository.save(product);

        return product;
    }


    // achar um unico produto
    public Product findById(int id){

       return productRepository.findById(id);

    }

    // mostrar todos os produtos
    public List<Product> findAll(){

        return productRepository.findAll();
    }

    // fazebndo update em um produto
    public Product updateProduct(Product product){

        Product newProduct = productRepository.findById(product.getId());
        newProduct.setName(product.getName());
        newProduct.setPrice(product.getPrice());
        newProduct.setDescription(product.getDescription());
        newProduct.setIngredients(product.getIngredients());
        newProduct.setImage(product.getImage());
        newProduct.setCategory(product.getCategory());

        productRepository.save(newProduct);

        return newProduct;

    }

    // delete um produto
    public String deleteProduct(int id){

        productRepository.delete(productRepository.findById(id)); 

        return "product deleted successfully";
    }
   
}