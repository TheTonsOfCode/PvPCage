package com.noname.pvpcage.utilities.data;

import com.noname.pvpcage.PvPCage;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
                    StringBuilder table = new StringBuilder();
                    table.append("CREATE TABLE IF NOT EXISTS `UserDataPvPCage` (")
                    .append("`id` INT(6) NOT NULL AUTO_INCREMENT, ")
                    .append("`uuid` VARCHAR(36) NOT NULL UNIQUE, ")
                    .append("`name` VARCHAR(20) NOT NULL UNIQUE, ")
                    .append("`loseduel` INT(12), ")
                    .append("`winduel` INT(12), ")
                    .append("`escapeduel` INT(12), ")
                    .append("`points` INT(12), ")
                    .append("`victims` VARCHAR(100), ")
                    .append("PRIMARY KEY (`id`), ")
                    .append("KEY (`name`, `uuid`))");
                    PreparedStatement st = null;
                    try {
                        st = PvPCage.getMySQL().getConnection().prepareStatement(table.toString());
                        st.executeUpdate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    return "Create Table if not exists UserDataPvPCage";//i dalej co tam ma byc
                }
            };

    private Table() {

    }

    public abstract String getCreateTableQuery();

    public String getTableName() {
        return "PVP" + name();
    }

}
