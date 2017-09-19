package com.bit.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * doc https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-usagenotes-connect-drivermanager.html#connector-j-examples-connection-drivermanager
 * 仅供教学使用，仅抽象了db driver 和 config
 * 保留了个人的 .idea 文件，方便载入
 * 需要知识 : Java SQL
 * */
public class Main {

    private Connection conn = null;
    private ConfigManager configManager = null;

    public Main() {
        init();
    }

    private void init() {
        this.configManager = ConfigManager.getInstance();
        String dbUser = configManager.getString("user");
        String dbPassword = configManager.getString("password");
        String dbName = configManager.getString("database");

        try {
            conn = LoaderDriver.newInstance(dbUser, dbPassword, dbName);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Map<String, String>> query(String sql) {

        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        if (sql != null) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);

                ResultSetMetaData metaData = rs.getMetaData();
                int cols = metaData.getColumnCount();
                List<String> colNames = new ArrayList<String>(cols);

                for (int i = 1; i <= cols; i++) {
                    colNames.add(metaData.getColumnName(i));
                }

                while (rs.next()) {
                    Map<String, String> item = new HashMap<String, String>();
                    for (int i = 1; i <= cols; i++) {
                        item.put(colNames.get(i-1), String.valueOf(rs.getObject(i)));
                    }
                    result.add(item);
                }

                return result;

            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ignored) {
                        //
                    }
                    rs = null;
                }

                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException ignored) {
                        //
                    }
                    stmt = null;
                }
            }
        }
        return result;
    }

    public void close() {
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (SQLException ignored) {
                //
            }
            this.conn = null;
        }
    }

    public static void main(String[] args) {
        Main main = new Main();

        String sql = "SELECT * FROM study.order";
        List<Map<String, String>> result = main.query(sql);
        for (Map<String, String> column : result) {
            for (Map.Entry<String, String> item : column.entrySet()) {
                System.out.println(
                        String.format("%s:%s", item.getKey(), item.getValue())
                );
            }
            System.out.println();
        }

        main.close();
    }
}
