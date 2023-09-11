package com.fome.projectfome.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fome.projectfome.model.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
    public Product findById(int id);
    public List<Product> findAll();
}