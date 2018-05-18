package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.db.DBManager;

public class Service {

    public Boolean login(String username, String password) {

        // Get sql queries
        String logSql = "select * from user_info where user_name ='" + username
                + "' and user_password ='" + password + "'";

        // get database instance
        DBManager sql = DBManager.createInstance();
        sql.connectDB();

        // operate database instance
        try {
            ResultSet rs = sql.executeQuery(logSql);
            if (rs.next()) {
                sql.closeDB();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql.closeDB();
        return false;
    }

    public Boolean register(String username, String password) {
    
        // Get sql queries
        String regSql = "insert into user_info values('"+ username+ "','"+ password+ "') ";  //this need to be modified

        // operate database instance
        DBManager sql = DBManager.createInstance();
        sql.connectDB();

        int ret = sql.executeUpdate(regSql);
        if (ret != 0) {
            sql.closeDB();
            return true;
        }
        sql.closeDB();
        
        return false;
    }
}