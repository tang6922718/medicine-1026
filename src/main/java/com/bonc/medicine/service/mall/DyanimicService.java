package com.bonc.medicine.service.mall;


import com.bonc.medicine.entity.mall.Dyanimic;

import java.util.List;
import java.util.Map;

public interface DyanimicService {

	public int insertDyanimic(Dyanimic dyanimic); // 发布动态
	public List<Map> selectAllDyanimic(int dyn_cat_id, String publish_time);		// 查询所有种类动态
	public List<Map> selectAllDyanimic(String dyn_cat_id, String publish_time, String words);		// 查询所有种类动态
	public List<Map> selectUserDyanimic(int publish_user_id,int dyn_cat_id); //查询某一用户的动态
	public List<Map> selectDetailOneDyanimic(int id); //查询某一条动态
	public int delOneDyanimic(int id);//删除某一条id
	public List<Map> selectDyanimicCategory();//查询动态分类
	public List<Map> selectUncheckDyanimic(int dyn_cat_id,String publish_time);//查询待审核的动态
	public int updateOneDyanimic( char effect_flag, String fail_opinion,int id);//审核 动态，批准0或不批准1
	public List<Map> selectJoinDyanimic( int user_id);// 我参与（不包括我发布的）的动态



}
