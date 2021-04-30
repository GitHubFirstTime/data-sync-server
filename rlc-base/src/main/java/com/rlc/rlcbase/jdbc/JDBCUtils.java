package com.rlc.rlcbase.jdbc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class JDBCUtils {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Connection conn;
    private String classDriver;
    private String url;
    private String userName;
    private String passWord;
    DBConfig dbConfig;
    public JDBCUtils(String classDriver, String url, String userName, String passWord) {
        this.classDriver = classDriver;
        this.url = url;
        this.userName = userName;
        this.passWord = passWord;
        dbConfig = new DBConfig();
        dbConfig.setDriverClassName(classDriver);
        dbConfig.setUrl(url);
        dbConfig.setUserName(userName);
        dbConfig.setPassWord(passWord);
        conn = DBConnectUtils.getconnection(dbConfig);
    }
    public static List<Map<String, Object>> toListMap(ResultSet ret) throws SQLException{
        List<Map<String, Object>> list = Lists.newArrayList();
        ResultSetMetaData meta = ret.getMetaData();
        int cot = meta.getColumnCount();

        while(ret.next()) {
            Map<String, Object> map = Maps.newHashMap();
            for(int i = 0; i < cot; i++) {
                map.put(meta.getColumnName(i + 1), ret.getObject(i + 1));
            }
            list.add(map);
        }

        return list;
    }

    public Map<String,Object> queryMap(String querySql){
        Map<String,Object> resultMap = Maps.newHashMap();

        try {
            Statement statement = (Statement) conn.createStatement();
            ResultSet rs = statement.executeQuery(querySql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultMap;
    }
    public List<Map<String,Object>> queryListMap(String querySql){
        List<Map<String,Object>> resultMapList = Lists.newArrayList();
//        resultMapList = jdbcTemplate.queryForList(querySql);
        try {
            Statement statement = (Statement) conn.createStatement();
            ResultSet rs = statement.executeQuery(querySql);
            resultMapList = toListMap(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultMapList;
    }

}
