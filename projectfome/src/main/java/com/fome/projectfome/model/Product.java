package com.fome.projectfome.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.persistence.GeneratedValue;



import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import javax.persistence.*;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Entity
@SQLDelete(sql = "UPDATE products SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
@Table(name = "products")

public class Product {
    
    @Id
    @NotNull(message = "O campo ID nao pode ser vazio.")
    @GeneratedValue
    private int id;
    
    @NotNull(message = "O campo name nao pode ser vazio.")
    private String name;

    @NotNull(message = "O campo price nao pode ser vazio.")
    private Double price;

    @NotNull(message = "O campo totalValue nao pode ser vazio.")
    private String description;

    @NotNull(message = "O campo ingredients nao pode ser vazio.")
    public ArrayList<String> ingredients = new ArrayList<String>();
    
    private boolean deleted = Boolean.FALSE;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category", referencedColumnName = "id", nullable = true)
    private Category category;

    private String image;

    public Product (){

    }

    public Product(String name, Double price, String description, String image){

        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public void insertIngredient(ArrayList<String> Ingredients){

        ingredients.addAll(this.ingredients);

    }

}