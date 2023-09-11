package com.fome.projectfome.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.persistence.GeneratedValue;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;


import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.fome.projectfome.dto.enums.EPaymentMethod;
import com.fome.projectfome.dto.enums.EStatusOrder;

@Entity
@SQLDelete(sql = "UPDATE orders SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
@Table(name = "orders")

public class Order {
    
    @Id
    @NotNull(message = "O campo ID nao pode ser vazio.")
    @GeneratedValue
    private int id;

    @NotNull(message = "O campo price nao pode ser vazio.")
    private Double totalValue;

    private EPaymentMethod paymentMethod;

    @NotNull(message = "O campo idProductOrder nao pode ser vazio.")
    public ArrayList<String> idProductOrder = new ArrayList<String>();

    @NotNull(message = "O campo idProductOrder nao pode ser vazio.")
    public ArrayList<String> newIdProductOrder = new ArrayList<String>();
    
    private boolean deleted = Boolean.FALSE;

    private String cpfCliente;

    private EStatusOrder status = EStatusOrder.PREPARING;

    @ManyToOne(optional = false)
    @JoinColumn(name = "table_id", referencedColumnName = "id", nullable = true)
    private User table_id;

    @CreationTimestamp 
    private Date createdAt;

    @UpdateTimestamp 
    private Date updatedAt;

    public Order (){

    }

    public Order( Double totalValue, EPaymentMethod paymentMethod){

        this.totalValue = totalValue;
        this.paymentMethod = paymentMethod;
        
    }


}