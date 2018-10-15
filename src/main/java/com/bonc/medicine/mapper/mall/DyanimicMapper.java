package com.bonc.medicine.mapper.mall;

import com.bonc.medicine.entity.mall.Dyanimic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface DyanimicMapper {


	public int insertDyanimic(Dyanimic dyanimic); // 发布动态
	public List<Map> selectAllDyanimicTwo(@Param("dyn_cat_id") String dyn_cat_id,
									   @Param("publish_time") String publish_time,	// 查询所有种类动态
									   @Param("words") String words);		// 查询所有种类动态

	public List<Map> selectAllDyanimic(@Param("dyn_cat_id") int dyn_cat_id,
									   @Param("publish_time") String publish_time);	// 查询所有种类动态

	public List<Map> selectUserDyanimic(@Param("publish_user_id") int publish_user_id,
										@Param("dyn_cat_id") int dyn_cat_id); //查询某一用户的动态

	public List<Map> selectDetailOneDyanimic(@Param("id") int id); //查询某一条动态
	public int delOneDyanimic(@Param("id") int id);//删除某一条id
	public List<Map> selectDyanimicCategory();//查询动态分类
	public List<Map> selectUncheckDyanimic(@Param("dyn_cat_id") int dyn_cat_id,
										   @Param("publish_time") String publish_time);//查询待审核的动态

	public int updateOneDyanimic(@Param("effect_flag") char effect_flag,
								 @Param("fail_opinion") String fail_opinion,
								 @Param("id") int id);//审核 动态，批准0或不批准1

	public List<Map> selectJoinDyanimic(@Param("user_id") int user_id);// 我参与（不包括我发布的）的动态



}
