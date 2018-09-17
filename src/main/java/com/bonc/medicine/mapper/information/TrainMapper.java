package com.bonc.medicine.mapper.information;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

public interface TrainMapper {


    @InsertProvider(type = TrainDynaSqlProvider.class,
            method = "createTrain")
    int createTrain(Map<String, Object> map);


    @InsertProvider(type = TrainDynaSqlProvider.class,
            method = "createVideo")
    int createVideo(Map<String, Object> map);

    @InsertProvider(type = TrainDynaSqlProvider.class,
            method = "addComment")
    int addComment(Map<String, Object> map);


    @Select("select  * from train_interact  where  object_id=#{object_id}  ORDER BY  interact_time limit 0,100")
    List<Map> selectComment(Map<String, Object> map);


    @SelectProvider(type = TrainDynaSqlProvider.class,
            method = "selectCourseList")
    List<Map> selectCourseList(Map<String, Object> map);


    @Insert("insert into train_appointment(id,object_id,object_type,user_id) values (#{id},#{object_id},#{object_type},#{user_id})")
    int addTrainApply(Map<String, Object> map);


    @SelectProvider(type = TrainDynaSqlProvider.class,
            method = "selectTrainList")
    List<Map> selectTrainList(Map<String, Object> map);


    @Insert("insert into train_offline_video(id,train_id,video_url,upload_user_id) values (#{id},#{train_id},#{video_url},#{upload_user_id}) ")
    int editOfflineTrainVideo(Map<String, Object> map);


    @UpdateProvider(type = TrainDynaSqlProvider.class,
            method = "editOfflineTrain")
    int editOfflineTrain(Map<String, Object> map);


    @Update("update train_offline set is_display='0' where id=#{id}")
    int delOfflineTrain(Map<String, Object> map);


    @Update("update train_offline_video set effect_flag='0' where id=#{id}")
    int delOfflineTrainVideo(Map<String, Object> map);


    @Select("select * from  train_course_category")
    @ResultType(List.class)
    List<Map> selectCourseClass();


  /*  @Select("select * from  train_live  where id in (select object_id from train_appointment where user_id=#{user_id} and  object_type=#{object_type})")
    @ResultType(List.class)*/


    @SelectProvider(type = TrainDynaSqlProvider.class,
            method = "selectTrainApply")
    List<Map> selectTrainApply(Map<String, Object> map);


    class TrainDynaSqlProvider {
        public String createTrain(final Map<String, Object> map) {
            return new SQL() {{
                INSERT_INTO("train_offline");
                if (map.get("user_id") != null) {
                    VALUES("user_id", "#{user_id}");
                }
                if (map.get("title") != null) {
                    VALUES("title", "#{title}");
                }
                if (map.get("train_type") != null) {
                    VALUES("train_type", "#{train_type}");
                }
                if (map.get("lecturer_name") != null) {
                    VALUES("lecturer_name", "#{lecturer_name}");
                }
                if (map.get("train_time") != null) {
                    VALUES("train_time", "#{train_time}");
                }
                if (map.get("duration") != null) {
                    VALUES("duration", "#{duration}");
                }
                if (map.get("address") != null) {
                    VALUES("address", "#{address}");
                }
                if (map.get("img_url") != null) {
                    VALUES("img_url", "#{img_url}");
                }
                if (map.get("lecturer_introduce") != null) {
                    VALUES("lecturer_introduce", "#{lecturer_introduce}");
                }
                if (map.get("train_introduce") != null) {
                    VALUES("train_introduce", "#{train_introduce}");
                }
            }}.toString();
        }

        public String createVideo(final Map<String, Object> map) {
            return new SQL() {{
                INSERT_INTO("train_video_course");
                if (map.get("id") != null) {
                    VALUES("id", "#{id}");
                }
                if (map.get("title") != null) {
                    VALUES("title", "#{title}");
                }
                if (map.get("video_type") != null) {
                    VALUES("video_type", "#{video_type}");
                }
                if (map.get("lecturer_name") != null) {
                    VALUES("lecturer_name", "#{lecturer_name}");
                }
                if (map.get("duration") != null) {
                    VALUES("duration", "#{duration}");
                }
                if (map.get("video_url") != null) {
                    VALUES("video_url", "#{video_url}");
                }
                if (map.get("img_url") != null) {
                    VALUES("img_url", "#{img_url}");
                }
                if (map.get("course_introduce") != null) {
                    VALUES("course_introduce", "#{course_introduce}");
                }
                if (map.get("user_id") != null) {
                    VALUES("user_id", "#{user_id}");
                }
                if (map.get("spec_id") != null) {
                    VALUES("spec_id", "#{spec_id}");
                }

            }}.toString();
        }

        public String addComment(final Map<String, Object> map) {
            return new SQL() {{
                INSERT_INTO("train_interact");
                if (map.get("object_id") != null) {
                    VALUES("object_id", "#{object_id}");
                }
                if (map.get("object_type") != null) {
                    VALUES("object_type", "#{object_type}");
                }
                if (map.get("user_id") != null) {
                    VALUES("user_id", "#{user_id}");
                }
                if (map.get("content") != null) {
                    VALUES("content", "#{content}");
                }


            }}.toString();
        }

        public String selectCourseList(final Map<String, Object> map) {
            return new SQL() {{
                SELECT("*");
                FROM("train_video_course");
                if (map.get("video_type") != null) {
                    WHERE("video_type=#{video_type}");
                }
                if (map.get("id") != null) {
                    WHERE("id=#{id}");
                }
            }}.toString();
        }

        public String selectTrainList(final Map<String, Object> map) {
            return new SQL() {{
                SELECT("*");
                FROM("train_offline");
                if (map.get("id") != null) {
                    WHERE("id=#{id}");
                }

            }}.toString();
        }

        public String editOfflineTrain(final Map<String, Object> map) {
            return new SQL() {{
                UPDATE("train_offline");

                if (map.get("title") != null) {
                    SET("title=#{title}");
                }
                if (map.get("train_type") != null) {
                    SET("train_type=#{train_type}");
                }
                if (map.get("train_introduce") != null) {
                    SET("train_introduce=#{train_introduce}");
                }

                if (map.get("lecturer_introduce") != null) {
                    SET("lecturer_introduce=#{lecturer_introduce}");
                }
                if (map.get("lecturer_name") != null) {
                    SET("lecturer_name=#{lecturer_name}");
                }
                if (map.get("address") != null) {
                    SET("address=#{address}");
                }

                if (map.get("train_time") != null) {
                    SET("train_time=#{train_time}");
                }
                if (map.get("duration") != null) {
                    SET("duration=#{duration}");
                }
                if (map.get("img_url") != null) {
                    SET("img_url=#{img_url}");
                }

                if (map.get("status") != null) {
                    SET("status=#{status}");
                }
                if (map.get("user_id") != null) {
                    SET("user_id=#{user_id}");
                }
                if (map.get("fail_opinion") != null) {
                    SET("fail_opinion=#{fail_opinion}");
                }
                WHERE("id=#{id}");
            }}.toString();
        }

        public String selectTrainApply(final Map<String, Object> map) {
            return new SQL() {{
                SELECT("*");

                switch (Integer.valueOf(String.valueOf(map.get("object_type")))) {
                    case 1:
                        FROM("train_live");
                        break;
                    case 2:
                        FROM("train_video_course");
                        break;
                    default:
                        FROM("train_live");
                        break;
                }
//                where id in (select object_id from train_appointment where user_id=#{user_id} and  object_type=#{object_type})
                if (map.get("user_id") != null && map.get("object_type")!=null) {
                    WHERE("id in (select object_id from train_appointment where user_id=#{user_id} and  object_type=#{object_type})");
                }

            }}.toString();
        }


    }
}
