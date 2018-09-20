package com.bonc.medicine.mapper.field;

import com.bonc.medicine.entity.field.Field;
import com.bonc.medicine.entity.field.FieldRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public interface FieldManageMapper {

    public int insertField(Field tempData);

    public Map queryUserInfo(int ID); // 根据用户ID去user表查询name   tel

    public Map queryCoopName(int ID); // 根据用户ID查询其所属合作社的名字

    public List<Map> getfield(Map param);

    public int addOperation(FieldRecord tempData);

    public List<Map> getOperation(Map param);

    public int updateFieldInfo(@Param("fieldID") int fieldID, @Param("date") Date date); //更新地块的最新更新时间

    public List<Map> guideRecord(Map param);

    public List<Map> guideRecordNum(Map param);

    public List<Map> queryFarmOpreationByCategroy(int categroyID);

    public Map querySOP(Map param);

    public List<Map> queryAllCategroy();

    public int insertFieldPlantCategroysInfo(Map param);

    public List<Map> queryFieldCategroyInfo(int fieldID);

}
