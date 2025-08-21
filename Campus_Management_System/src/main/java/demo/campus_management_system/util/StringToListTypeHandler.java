package demo.campus_management_system.util;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class StringToListTypeHandler extends BaseTypeHandler<List<String>> {

    // 分割符（根据实际情况调整）
    private static final String SPLITTER = ",";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        // 插入数据库时，将List转换为字符串（可选实现）
        if (parameter != null && !parameter.isEmpty()) {
            String joined = String.join(SPLITTER, parameter);
            ps.setString(i, joined);
        } else {
            ps.setString(i, null);
        }
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // 查询时，将字符串分割为List
        String value = rs.getString(columnName);
        return splitString(value);
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return splitString(value);
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return splitString(value);
    }

    // 核心分割逻辑
    private List<String> splitString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return Arrays.asList(value.split(SPLITTER));
    }
}