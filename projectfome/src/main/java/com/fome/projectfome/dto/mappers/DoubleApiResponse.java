package com.fome.projectfome.dto.mappers;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoubleApiResponse{

    private boolean success;
    private Double result;

    public DoubleApiResponse(boolean success, Double result){

        this.success = success;
        this.result = result;

    }

}