package com.fome.projectfome.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fome.projectfome.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer>{
    public User findById(int id);
    public List<User> findAll();
    public Iterable<User> findByUsername(String username);
}
