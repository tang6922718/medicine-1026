package com.bonc.medicine.mapper.information;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

public interface InfoMapper {

    @Select({"<script>",
            "SELECT * FROM `info_basic`",
            "WHERE is_display='1'",
            "<when test='catCode!=null' >",
            "AND cat_code = #{catCode}",
            "</when>",
            "</script>"})
    @ResultType(List.class)
    List<Map> getAllInfo(@Param("catCode") String catCode);


    @InsertProvider(type=InfoDynaSqlProvider.class,
            method="addInfo")
    int addInfo(Map<String, Object> map);


    @Select("SELECT * FROM `info_basic` where id=#{id}")
    @ResultType(Map.class)
    Map infoDetailById(String id);

    @UpdateProvider(type=InfoDynaSqlProvider.class,
            method="infoEditById")
    int infoEditById(Map<String, Object> map);

    @Insert("insert into km_audit(title,km_type,object_id,status,fail_opinion) values (#{title},#{cat_code},#{id},#{audit_result},#{audit_info})")
    int infoAuditInsert(Map<String, Object> map);


    @Select("select * from info_basic where cat_code=#{cat_code}")
    List<Map> infoClass(Map<String, Object> map);

    //更新阅读量+1，并返回阅读量
    @Select("select read_count from info_basic where id=#{id}")
    Map infoReadCount(Map map);

    @Update("update info_basic set read_count=read_count+1 where id =#{id}")
    int updateReadCount(Map map);




    @Update("update info_basic  set  is_display='0' where id =#{id}")
    int delInfo(Map<String, Object> map);


    @Update("update info_basic  set  status='0' where id =#{id}")
    int infoRepeal(String id);

    class InfoDynaSqlProvider {
        public String addInfo(final Map<String,Object> map)
        {
            return new SQL() {{
                INSERT_INTO("info_basic");
                if (map.get("id") != null) {
                    VALUES("id", "#{id}");
                }
                if (map.get("title") != null) {
                    VALUES("title", "#{title}");
                }
                if (map.get("source_code") != null) {
                    VALUES("source_code", "#{source_code}");
                }
                if (map.get("cat_code") != null) {
                    VALUES("cat_code", "#{cat_code}");
                }
                if (map.get("img_url") != null) {
                    VALUES("img_url", "#{img_url}");
                }
                if (map.get("is_top_line") != null) {
                    VALUES("is_top_line", "#{is_top_line}");
                }
                if (map.get("is_market_top") != null) {
                    VALUES("is_market_top", "#{is_market_top}");
                }
                if (map.get("is_alarm") != null) {
                    VALUES("is_alarm", "#{is_alarm}");
                }
                if (map.get("content") != null) {
                    VALUES("content", "#{content}");
                }
                if (map.get("user_id") != null) {
                    VALUES("user_id", "#{user_id}");
                }
            }}.toString();
        }

        public String infoEditById(final Map<String,Object> map)
        {
            return new SQL() {{
                UPDATE("info_basic");
                if (map.get("title") != null) {
                    SET("title=#{title}");
                }
                if (map.get("cat_code") != null) {
                    SET("cat_code=#{cat_code}");
                }
                if (map.get("img_url") != null) {
                    SET("img_url=#{img_url}");
                }
                if (map.get("is_head_line") != null) {
                    SET("is_head_line=#{is_head_line}");
                }
                if (map.get("img_url") != null) {
                    SET("img_url=#{img_url}");
                }
                if (map.get("is_top_line") != null) {
                    SET("is_top_line=#{is_top_line}");
                }
                if (map.get("is_market_top") != null) {
                    SET("is_market_top=#{is_market_top}");
                }
                if (map.get("is_alarm") != null) {
                    SET("is_alarm=#{is_alarm}");
                }
                if (map.get("content") != null) {
                    SET("content=#{content}");
                }
                if (map.get("user_id") != null) {
                    SET("user_id=#{user_id}");
                }
                if (map.get("audit_result") != null) {
                    SET("status=#{audit_result}");
                }
                WHERE("id=#{id}");
            }}.toString();
        }

    }
}
