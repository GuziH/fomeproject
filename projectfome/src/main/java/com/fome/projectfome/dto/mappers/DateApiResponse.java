package com.fome.projectfome.dto.mappers;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateApiResponse {

    private boolean success;
    private Date iniDate;
    private Date finalDate;

    public DateApiResponse(boolean success, Date iniDate, Date finalDate) {
        this.success = success;
        this.iniDate = iniDate;
        this.finalDate = finalDate;
    }
    
}
