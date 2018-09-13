package com.bonc.medicine.mapper.information;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

public interface CommonMapper {
int insert(@Param("sql") String sql);
int update(@Param("sql") String sql);
List<LinkedHashMap<String,Object>> query(@Param("sql") String sql);
Integer getCount(@Param("name") String table_name, @Param("where") String whereClause);

}
