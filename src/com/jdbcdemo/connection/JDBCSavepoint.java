package com.jdbcdemo.connection;

import java.sql.*;

/**
 * @description: JDBC设置保存点
 * @author: jibingbing
 * @create: 2018/02/15
 **/
public class JDBCSavepoint {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/servlet";
    static final String USERNAME = "root";
    static final String PASSWORD = "root";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        String sql = null;
        try {
            //注册JDBC驱动
            Class.forName(JDBC_DRIVER);
            //获取数据库连接
            conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            //自动提交设为false
            conn.setAutoCommit(false);

            stmt = conn.createStatement();

            sql = "select * from Employees";
            ResultSet rs = stmt.executeQuery(sql);
            printRs(rs);
            //设置事务保存点
            Savepoint savepoint1 = conn.setSavepoint("Row_delete_1");
            System.out.println("Deleting row_1...");
            sql = "DELETE FROM Employees WHERE id = 103";
            int i1 = stmt.executeUpdate(sql);
            System.out.println("i1 = " + i1);

            Savepoint savepoint2 = conn.setSavepoint("Row_delete_2");
            System.out.println("Deleting row_2...");
            sql = "DELETE FROM Employees WHERE id = 105";
            int i2 = stmt.executeUpdate(sql);
            System.out.println("i2 = " + i2);
            //回滚事务到指定的保存点
            conn.rollback(savepoint2);

            sql = "SELECT * FROM Employees";
            rs = stmt.executeQuery(sql);
            printRs(rs);
            //提交事务
            conn.commit();

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
            System.out.println("Rolling back data here...");
            try{
                if(conn != null){
                    conn.rollback();
                }
            } catch (SQLException se2){
                se2.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(stmt == null){
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

    public static void printRs(ResultSet rs) throws SQLException{
        rs.beforeFirst();
        while(rs.next()){
            int id = rs.getInt("id");
            int age = rs.getInt("age");
            String first = rs.getString("first");
            String last = rs.getString("last");

            System.out.println("ID: " + id + ", Age: " + age + ", First: " + first + ", Last: " + last);
        }
    }
}
