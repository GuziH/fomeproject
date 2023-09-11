package com.fome.projectfome.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fome.projectfome.model.Category;


@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {
    public Category findById(int id);
    public Category findByName(String name);
    public List<Category> findAll();

    @Query(value = "SELECT * FROM categorys WHERE category_id=?1", nativeQuery = true)
    public Category findById2(int id);
}
