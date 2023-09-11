package com.fome.projectfome.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Entity
@SQLDelete(sql = "UPDATE categorys SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
@Table(name = "categorys")
public class Category {
    
    @Id
    @NotNull(message = "O campo ID nao pode ser vazio.")
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @NotNull(message = "The attribute NAME cannot be null")
    private String name;

    private boolean deleted = Boolean.FALSE;

    public Category(){}

    public Category(int categoryId, String name){

        this.id = categoryId;
        this.name = name;
    }
}
