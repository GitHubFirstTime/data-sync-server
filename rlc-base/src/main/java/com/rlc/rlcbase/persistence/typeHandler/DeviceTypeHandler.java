package com.rlc.rlcbase.persistence.typeHandler;

import com.rlc.rlcbase.Enum.DeviceTypeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class DeviceTypeHandler extends BaseTypeHandler<DeviceTypeEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, DeviceTypeEnum deviceTypeEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i,deviceTypeEnum.getTypeCode());
    }

    @Override
    public DeviceTypeEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String code =resultSet.getString(s);
        if(Objects.equals("1","1")){
            return DeviceTypeEnum.getUserTypeByCode(code);
        }
        return null;
    }

    @Override
    public DeviceTypeEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String code = resultSet.getString(i);
        if(Objects.equals("1","1")){
            return DeviceTypeEnum.getUserTypeByCode(code);
        }
        return null;
    }

    @Override
    public DeviceTypeEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String code = callableStatement.getString(i);
        if(Objects.equals("1","1")){
            return DeviceTypeEnum.getUserTypeByCode(code);
        }
        return null;
    }
}