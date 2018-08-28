package com.jdbcdemo.connection;

import java.sql.*;

/**
 * @description: JDBC连接实例
 * @author: jibingbing
 * @create: 2018/02/14
 **/
public class ConnectionExample {
    static final String JDBC_DRIVER = "com.mysql.jdbc.driver";
    static final String DB_URL = "jdbc:mysql://localhost/servlet";
    static final String USERNAME = "root";
    static final String PASSWORD = "root";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //获取数据库连接
            System.out.println("connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            //创建statement对象
            System.out.println("creating statement...");
//            stmt = conn.createStatement();
            //创建sql语句
//            String sql = "select * from Employees";
            String sql = "select * from Employees where age = ?";
            //执行sql语句
//            ResultSet rs = stmt.executeQuery(sql);
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,23);
            ResultSet rs = pstmt.executeQuery();
            //遍历结果集
            while(rs.next()){
                int id = rs.getInt("id");
                int age = rs.getInt("age");
                String first = rs.getString("first");
                String last = rs.getString("last");

                System.out.println("ID: " + id + ", AGE: " + age + ", FIRST: " + first + ", LAST: " + last);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(pstmt == null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn == null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
