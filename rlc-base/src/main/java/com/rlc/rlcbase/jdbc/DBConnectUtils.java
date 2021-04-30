package com.rlc.rlcbase.jdbc;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConnectUtils {
    //1.定义成员变量
    private static DataSource ds;
  /*  static {

        try {
            //1.加载配置文件
            Properties properties = new Properties();
            properties.load(JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties"));
            //2.获取DataSource,进行初始化连接池
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    /**
     *@Description: 返回connection连接对象
     *@Param: 无
     *@return: connection对象
     */
    public static Connection getconnection(DBConfig dbConfig){
        Connection connection = null;
        try {
            ds = getDataSource(dbConfig);
            connection = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    /**
     *@Description: 释放连接
     *@Param: Statement stmt,Connection cnn
     *@return: 无
     */
    public static void close(Statement stmt,Connection cnn){
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(cnn!=null){
            try {
                cnn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    /**
     *@Description: 释放连接
     *@Param: Statement stmt,Connection cnn, ResultSet res
     *@return: 无
     */
    public static void close(Statement stmt, Connection cnn, ResultSet res){
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(cnn!=null){
            try {
                cnn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(res!=null){
            try {
                res.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    /**
     *@Description: 返回数据库连接池对象（用于Spring 封装的jdbc的框架 和一些只需要数据库连接池对象的框架）
     *@Param:
     *@return:
     */
    public static DataSource getDataSource(DBConfig dbConfig) {
        //1.加载配置文件
        try {
            Properties properties = new Properties();
            properties.setProperty("driverClassName",dbConfig.getDriverClassName());
            properties.setProperty("url",dbConfig.getUrl());
            properties.setProperty("username",dbConfig.getUserName());
            properties.setProperty("password",dbConfig.getPassWord());
//            properties.load(JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties"));
            //2.获取DataSource,进行初始化连接池
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
}
