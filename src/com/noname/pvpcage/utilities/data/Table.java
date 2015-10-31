package com.noname.pvpcage.utilities.data;

public enum Table {
    DEATHS() {
        @Override
        public String getCreateTableQuery() {
            return "Create Table if not exists " + getTableName();//i dalej co tam ma byc
        }      
    },
    TEAMS {
        @Override
        public String getCreateTableQuery() {
            return "Create Table if not exists " + getTableName();//i dalej co tam ma byc
        }      
    },
    USERS {
        @Override
        public String getCreateTableQuery() {
            return "Create Table if not exists " + getTableName();//i dalej co tam ma byc
        }      
    };

    private Table() {
        
    }
    
    public abstract String getCreateTableQuery();
    
    public String getTableName() {
        return "PVP" + name();
    }
    
}
