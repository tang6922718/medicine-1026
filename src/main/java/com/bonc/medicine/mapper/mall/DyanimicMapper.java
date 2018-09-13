package com.bonc.medicine.mapper.mall;

import com.bonc.medicine.entity.mall.Dyanimic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface DyanimicMapper {


	public int insertDyanimic(Dyanimic dyanimic); // 发布动态
	public List<Map> selectAllDyanimic();		// 查询所有种类动态
	public List<Map> selectOneDyanimic(int id); //查询某一类(除供求)动态
	public List<Map> selectTwoDyanimic(); //查询供求类动态
	public List<Map> selectUserOneDyanimic(int publish_user_id, int id); //查询某一用户除供求的动态
	public List<Map> selectUserTwoDyanimic(int publish_user_id); //查询某一用户的供求动态
	public List<Map> selectDetailOneDyanimic(int id); //查询某一条动态


}
