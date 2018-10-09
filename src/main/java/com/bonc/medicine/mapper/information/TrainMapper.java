package com.bonc.medicine.mapper.information;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import javax.management.ObjectName;
import java.util.List;
import java.util.Map;

public interface TrainMapper {


    @InsertProvider(type = TrainDynaSqlProvider.class,
            method = "createTrain")
    int createTrain(Map<String, Object> map);


    @InsertProvider(type = TrainDynaSqlProvider.class,
            method = "createVideo")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int createVideo(Map<String, Object> map);

    @InsertProvider(type = TrainDynaSqlProvider.class,
            method = "addComment")
    int addComment(Map<String, Object> map);


    @Select("select  t.*, u.name, ifnull(u.head_portrait,'1537932363932658') head_portrait from train_interact t inner join common_user u on t.user_id = u.id where  object_id=#{object_id}  ORDER BY  interact_time limit 0,100")
    List<Map> selectComment(Map<String, Object> map);


    @SelectProvider(type = TrainDynaSqlProvider.class,
            method = "selectCourseList")
    List<Map> selectCourseList(Map<String, Object> map);


    @Insert("insert into train_appointment(object_id,object_type,user_id) values (#{object_id},#{object_type},#{user_id})")
    int addTrainApply(Map<String, Object> map);


    @SelectProvider(type = TrainDynaSqlProvider.class,
            method = "selectTrainList")
    List<Map> selectTrainList(Map<String, Object> map);


    @Insert("insert into train_offline_video(train_id,video_title,video_url,upload_user_id) values (#{train_id},#{video_title},#{video_url},#{user_id}) ")
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


    @Select("SELECT COUNT(id)  as bmNum from train_appointment  WHERE object_id=#{Appointment_id} and object_type=#{Appointment_type}  GROUP BY user_id ")
    Map queryAppointmentNumber(Map<String, String> map);


    //评论统计  todo
    @Select("SELECT COUNT(id)  as bmNum from train_appointment  WHERE object_id=#{comment_id} and object_type=#{Appointment_type}  GROUP BY user_id ")
    Map queryCommentNumber(Map<String, String> map);

    @Update("update train_video_course set status='1' where id=#{id}")
    int delCourseTrainVideo(Map<String, Object> map);


    @Update("update train_offline  set  operation_status='0' where id =#{id}")
    int repealOfflineTrain(Map<String, Object> map);

    @Update("update train_video_course  set  operation_status='0' where id =#{id}")
    int repealVideoCourse(Map<String, Object> map);


    @UpdateProvider(type = TrainDynaSqlProvider.class,
            method = "editVideoCourse")
    int editVideoCourse(Map<String, Object> map);

    //select spec_id,name  from  spec_info
    @Select({"<script>",
            "SELECT * FROM `spec_info` where 1=1",
            "<when test='specId!=null' >",
            "AND spec_id = #{specId}",
            "</when>",
            "</script>"})
    @ResultType(List.class)
    List<Map> selectSpecialist(@Param("specId") Integer specId);


    //查询是否报名
    @SelectProvider(type = TrainDynaSqlProvider.class,
            method = "selectApply")
    int selectApply(Map<String, Object> map);

    @Select("SELECT * FROM train_video_course a INNER JOIN (select object_id,view_num\n" +
            "from\n" +
            "common_view\n" +
            "WHERE object_type='4'\n" +
            "ORDER BY view_num DESC\n" +
            "LIMIT 0,5) b ON a.id = b.object_id")
    @ResultType(List.class)
    List<Map> selectVideoHot();


    int updateTrainStatus();


    class TrainDynaSqlProvider {
        public String createTrain(final Map<String, Object> map) {
            String sql = new SQL() {{
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
                if (map.get("spec_id") != null) {
                    VALUES("spec_id", "#{spec_id}");
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

            System.out.println(sql);
            return sql;
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
            String  sql=new SQL() {{
                SELECT("*");
                FROM("train_video_course");
                if (map.get("publish_time") != null && map.get("publish_time") != "") {
                    WHERE("publish_time >= #{publish_time}");
                }
                if (map.get("title") != null && map.get("title") != "") {
                    WHERE("title  like CONCAT('%',#{title},'%')");
                }
                if (map.get("video_type") != null) {
                    WHERE("video_type=#{video_type}");
                }
                if (map.get("id") != null) {
                    WHERE("id=#{id}");
                }
                WHERE("status='0'");
                ORDER_BY("publish_time desc");
            }}.toString();
            sql = "select b.*,a.* from (" + sql+") a inner join km_audit b on a.id=b.object_id and b.km_type='5'";
            System.out.println(sql);
            return sql;
        }

        public String selectTrainList(final Map<String, Object> map) {
            return new SQL() {{
                SELECT("*");
                FROM("train_offline a");
                LEFT_OUTER_JOIN("train_offline_video b on a.id =b.train_id");
                WHERE("is_display='1'");
                if (map.get("publish_time") != null && map.get("publish_time") != "") {
                    WHERE("publish_time >=#{publish_time}");
                }
                if (map.get("title") != null && map.get("title") != "") {
                    WHERE("title  like CONCAT('%',#{title},'%')");
                }
                if (map.get("id") != null) {
                    WHERE("a.id=#{id}");
                }
                ORDER_BY("publish_time desc");
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
                if (map.get("spec_id") != null) {
                    VALUES("spec_id", "#{spec_id}");
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

                SET("operation_status='1'");
                WHERE("id=#{id}");
            }}.toString();
        }

        public String selectTrainApply(final Map<String, Object> map) {
            return new SQL() {{
                SELECT("*");

                switch (Integer.valueOf(String.valueOf(map.get("object_type")))) {
                    case 1:
                        FROM("train_live");
                        if (map.get("user_id") != null && map.get("object_type") != null) {
                            WHERE("id in (select object_id from train_appointment where user_id=#{user_id} and  object_type=#{object_type})");
                        }
                        break;
                    case 2:
                        FROM("(SELECT * FROM train_offline");
                        if (map.get("user_id") != null && map.get("object_type") != null) {
                            WHERE("id in (select object_id from train_appointment where user_id=#{user_id} and  object_type=#{object_type}))a");
                        }
                        LEFT_OUTER_JOIN("train_offline_video b ON a.id= b.train_id");
                        break;
                    default:
                        FROM("train_live");
                        if (map.get("user_id") != null && map.get("object_type") != null) {
                            WHERE("id in (select object_id from train_appointment where user_id=#{user_id} and  object_type=#{object_type})");
                        }
                        break;
                }
//                where id in (select object_id from train_appointment where user_id=#{user_id} and  object_type=#{object_type})


            }}.toString();
        }

        public String editVideoCourse(final Map<String, Object> map) {
            return new SQL() {{
                UPDATE("train_video_course");

                if (map.get("title") != null) {
                    SET("title=#{title}");
                }
                if (map.get("video_type") != null) {
                    SET("video_type=#{video_type}");
                }
                if (map.get("lecturer_name") != null) {
                    SET("lecturer_name=#{lecturer_name}");
                }

                if (map.get("course_introduce") != null) {
                    SET("course_introduce=#{course_introduce}");
                }
                if (map.get("duration") != null) {
                    SET("duration=#{duration}");
                }
                if (map.get("video_url") != null) {
                    SET("video_url=#{video_url}");
                }

                if (map.get("img_url") != null) {
                    SET("img_url=#{img_url}");
                }

                SET("operation_status='1'");

                WHERE("id=#{id}");
            }}.toString();
        }

        public String selectApply(final Map<String, Object> map) {
            return new SQL() {{
                SELECT("count(id)");
                FROM("train_appointment");
                if (map.get("object_id") != null) {
                    WHERE("object_id=#{object_id}");
                }
                if (map.get("object_type") != null) {
                    WHERE("object_type=#{object_type}");
                }
                if (map.get("user_id") != null) {
                    WHERE("user_id=#{user_id}");
                }

            }}.toString();
        }
    }
}
