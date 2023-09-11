package com.fome.projectfome.dto.enums;

public enum EPaymentMethod {
    
    CASH {
        @Override
        public String toString(){
            return "CASH";
        }
    },

    DEBIT_CARD {
        @Override
        public String toString(){
            return "DEBIT_CARD";
        }
    }, 

    CREDIT_CARD {
        @Override
        public String toString(){
            return "CREDIT_CARD";
        }
    },

    PIX {
        @Override
        public String toString(){
            return "PIX";
        }
    },

}
