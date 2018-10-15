package com.bonc.medicine.mapper.information;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;


public interface LiveMapper {

    @InsertProvider(type = LiveDynaSqlProvider.class,
            method = "addLive")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addLive(Map<String, Object> map);


    @Update("update  train_live  set status=#{status} where room_id=#{room_id}")
    int updateLiveStatus(@Param("room_id") String room_id, @Param("status") String status);


    @SelectProvider(type = LiveDynaSqlProvider.class,
            method = "selectLive")
    @ResultType(List.class)
//    "select * from train_live"
    List<Map<String, Object>> selectAllLive(Map<String, Object> map);

    @Update({
            "<script>",
            "<foreach item='value' index='key' collection='map' separator=';'>",
            "update  train_live ",
            "set audience_num=audience_num + #{value} where room_id=#{key}",
            "</foreach>",
            "</script>"
    })
    int updateWatchPeople(Map map);


    @UpdateProvider(type = LiveDynaSqlProvider.class,
            method = "editLive" )
    int editLive(Map<String, Object> map);


    @Update("update train_live  set  operation_status='0' where id =#{id}")
    int repealLive(Map<String, Object> map);

    @Update("update train_live  set  is_display='0' where id =#{id}")
    int delLive(Map<String, Object> map);

    class LiveDynaSqlProvider {
        public String addLive(final Map<String, Object> map) {
            return new SQL() {{
                INSERT_INTO("train_live");
                if (map.get("id") != null) {
                    VALUES("id", "#{id}");
                }
                if (map.get("room_title") != null) {
                    VALUES("room_title", "#{room_title}");
                }
                if (map.get("room_desc") != null) {
                    VALUES("room_desc", "#{room_desc}");
                }
                if (map.get("room_desc") != null) {
                    VALUES("lecture_desc", "#{lecture_desc}");
                }
                if (map.get("room_id") != null) {
                    VALUES("room_id", "#{room_id}");
                }
                if (map.get("push_url") != null) {
                    VALUES("push_url", "#{push_url}");
                }
                if (map.get("pull_url") != null) {
                    VALUES("pull_url", "#{pull_url}");
                }
                if (map.get("pull_rtmp_url") != null) {
                    VALUES("pull_rtmp_url", "#{pull_rtmp_url}");
                }
                if (map.get("live_type") != null) {
                    VALUES("live_type", "#{live_type}");
                }
                if (map.get("spec_id") != null) {
                    VALUES("spec_id", "#{spec_id}");
                }
                if (map.get("lecture_name") != null) {
                    VALUES("lecture_name", "#{lecture_name}");
                }
                if (map.get("live_start") != null) {
                    VALUES("live_start", "#{live_start}");
                }
                if (map.get("live_end") != null) {
                    VALUES("live_end", "#{live_end}");
                }
                if (map.get("user_id") != null) {
                    VALUES("user_id", "#{user_id}");
                }
                if (map.get("img_url") != null) {
                    VALUES("img_url", "#{img_url}");
                }
                VALUES("create_time","CURRENT_TIMESTAMP()");
            }}.toString();
        }

        public String editLive(final Map<String, Object> map) {
            return new SQL() {{
                UPDATE("train_live");
                if(map.get("room_title") != null){
                    SET("room_title=#{room_title}");
                }
                if(map.get("live_type") != null){
                    SET("live_type=#{live_type}");
                }

                if(map.get("room_desc") != null){
                    SET("room_desc=#{room_desc}");
                }
                if(map.get("lecturer_desc") != null){
                    SET("lecturer_desc=#{lecturer_desc}");
                }
                if (map.get("spec_id") != null) {
                    VALUES("spec_id", "#{spec_id}");
                }
                if(map.get("live_start") != null){
                    SET("live_start=#{live_start}");
                }
                if(map.get("live_end") != null){
                    SET("live_end=#{live_end}");
                }
                if(map.get("img_url") != null){
                    SET("img_url=#{img_url}");
                }

                SET("operation_status='1'");
                WHERE("id=#{id}");
            }}.toString();
        }

        public String selectLive(final Map<String, Object> map) {

          /*  int start= (Integer.parseInt(String.valueOf(map.get("pageNum")))-1)*Integer.parseInt(String.valueOf(map.get("pageSize")));
            int end=Integer.parseInt(String.valueOf(map.get("pageSize")));*/

            String sql=new SQL() {{
                SELECT("*");
                FROM("train_live");
                if(map.get("live_start") != null && map.get("live_start") != ""){
                    WHERE("live_start>=#{live_start}");
                }
                if(map.get("room_title") != null && map.get("room_title") != ""){
                    WHERE("room_title  like CONCAT('%',#{room_title},'%')");
                }
                if(map.get("id") != null && map.get("id") != ""){
                    WHERE("id=#{id}");
                }
                WHERE("is_display='1'");
                ORDER_BY("create_time desc");
            }}.toString();
            System.out.println(sql);
            return sql;

        }


    }
}
