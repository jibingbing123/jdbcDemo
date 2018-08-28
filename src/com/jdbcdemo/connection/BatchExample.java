package com.jdbcdemo.connection;

import java.sql.*;
import java.util.Arrays;

/**
 * @description: 批量处理sql
 * @author: jibingbing
 * @create: 2018/02/15
 **/
public class BatchExample {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/servlet";
    static final String USERNAME = "root";
    static final String PASSWORD = "root";

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            conn.setAutoCommit(false);

            sql = "INSERT INTO Employees(id,age,first,last) VALUES (?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,200);
            pstmt.setInt(2,32);
            pstmt.setString(3,"ji");
            pstmt.setString(4,"wenwen");
            pstmt.addBatch();

            pstmt.setInt(1,201);
            pstmt.setInt(2,12);
            pstmt.setString(3,"wang");
            pstmt.setString(4,"wu");
            pstmt.addBatch();

            int[] count = pstmt.executeBatch();
            System.out.println("count: " + Arrays.toString(count));
            conn.commit();

            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(pstmt != null){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
