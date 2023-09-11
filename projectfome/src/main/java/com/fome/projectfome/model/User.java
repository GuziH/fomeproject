package com.fome.projectfome.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.persistence.GeneratedValue;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fome.projectfome.dto.enums.EUserRole;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
@Table(name = "users")
public class User {
    
    @Id
    @NotNull(message = "O campo ID nao pode ser vazio.")
    @GeneratedValue
    private int id;

    @NotEmpty(message = "username's field cannot be empty")
    private String username;

    @NotEmpty(message = "password's field cannot be empty")
    private String password;

    private EUserRole userRole;

    private boolean deleted = Boolean.FALSE;

    private String name;

    private String cpf;

    private Double salary;

    public User(){
        
    }

    public User(String username, String password, EUserRole userRole){

        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }

}
