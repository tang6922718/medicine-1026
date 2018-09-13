package com.bonc.medicine.mapper.information;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

public interface LiveMapper {

    @InsertProvider(type = LiveDynaSqlProvider.class,
            method = "addLive")
    int addLive(Map<String, Object> map);


    @Update("update  train_live  set status=#{status} where room_id=#{room_id}")
    int updateLiveStatus(@Param("room_id") String room_id, @Param("status") String status);


    @Select("select * from train_live")
    @ResultType(List.class)
    List<Map<String, Object>> selectAllLive();

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


    @Update("update train_live  set  is_display='0' where id =#{id}")
    int repealLive(Map<String, Object> map);

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
                if (map.get("lecture_name") != null) {
                    VALUES("lecture_name", "#{lecture_name}");
                }
                if (map.get("live_time") != null) {
                    VALUES("live_time", "#{live_time}");
                }
                if (map.get("fail_opinion") != null) {
                    VALUES("fail_opinion", "#{fail_opinion}");
                }
            }}.toString();
        }

        public String editLive(final Map<String, Object> map) {
            return new SQL() {{
                UPDATE("train_live");

                if(map.get("title") != null){
                    SET("title=#{title}");
                }
                if(map.get("train_type") != null){
                    SET("train_type=#{train_type}");
                }

                if(map.get("train_introduce") != null){
                    SET("train_introduce=#{train_introduce}");
                }
                if(map.get("lecturer_introduce") != null){
                    SET("lecturer_introduce=#{lecturer_introduce}");
                }

                if(map.get("address") != null){
                    SET("address=#{address}");
                }
                if(map.get("train_time") != null){
                    SET("train_time=#{train_time}");
                }

                if(map.get("duration") != null){
                    SET("duration=#{duration}");
                }
                if(map.get("img_url") != null){
                    SET("img_url=#{img_url}");
                }
                WHERE("id=#{id}");
            }}.toString();
        }

    }
}
