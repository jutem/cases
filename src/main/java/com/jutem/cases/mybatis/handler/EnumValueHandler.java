package com.jutem.cases.mybatis.handler;

import com.jutem.cases.mybatis.support.EnumValue;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes(EnumValue.class)
public class EnumValueHandler<E extends EnumValue> extends BaseTypeHandler<E> {

    private final Map<Integer, E> cache;

    public EnumValueHandler(Class<E> type) {
        if(type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        } else {
            cache = Arrays.stream(type.getEnumConstants())
                    .collect(Collectors.toMap(t -> t.getValue(), t -> t));
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return cache.get(rs.getInt(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return cache.get(rs.getInt(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cache.get(cs.getInt(columnIndex));
    }
}
