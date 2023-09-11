package com.fome.projectfome.dto.mappers;
import com.fome.projectfome.model.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListProductApiResponse implements Comparable<ListProductApiResponse> {
    
    private Product product;
    private int amount;

     public ListProductApiResponse(Product product, int amount){
        this.amount = amount;
        this.product = product;
     }

     @Override public int compareTo(ListProductApiResponse anotherProduct) { 
        if (this.amount > anotherProduct.getAmount()) { 
          return -1; 
          } if (this.amount < anotherProduct.getAmount()) { 
          return 1; 
          } 
          return 0; 
         }
}
