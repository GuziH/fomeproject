package com.fome.projectfome.dto.enums;

public enum EStatusOrder {
    
    PREPARING {
        @Override
        public String toString(){
            return "PREPARING";
        }
    },

    DONE {
        @Override
        public String toString(){
            return "DONE";
        }
    }, 

    PAID_OUT {
        @Override
        public String toString(){
            return "PAID_OUT";
        }
    }
}