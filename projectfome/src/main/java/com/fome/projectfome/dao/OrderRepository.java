package com.fome.projectfome.dao;

import java.util.List; 

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fome.projectfome.model.Order;


@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Integer> {
    public Order findById(int id);
    public List<Order> findAll();
    public List<Order> findByCpfCliente(String CpfClinte);
}