package com.fome.projectfome.dto.enums;

public enum EUserRole {
    
    ADMIN_ROLE {
        @Override
        public String toString(){
            return "ADMIN";
        }
    },

    TABLE_ROLE {
        @Override
        public String toString(){
            return "TABLE";
        }
    },

    WAITER_ROLE {
        @Override
        public String toString(){
            return "WAITER";
        }
    },

    ATTENDANT_ROLE {
        @Override
        public String toString(){
            return "ATTENDANT";
        }
    },

    KITCHEN_ROLE {
        @Override
        public String toString(){
            return "KITCHEN";
        }
    },


}