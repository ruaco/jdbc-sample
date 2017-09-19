package com.bit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class LoaderDriver {

    private LoaderDriver() {

    }

    static Connection newInstance(String user, String password, String database) throws SQLException {
        if (user == null || password == null || database == null) {
            return null;
        }
        Connection conn = null;

        String urlModel = "jdbc:mysql://localhost/%s?" +
                "user=%s&password=%s" +
                "&useUnicode=true&characterEncoding=UTF-8";
        try {
            conn = DriverManager.getConnection(
                    String.format(urlModel, database, user, password)
            );

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }

        return conn;
    }
}
