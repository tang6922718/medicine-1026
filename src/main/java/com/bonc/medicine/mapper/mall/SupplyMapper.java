package com.bonc.medicine.mapper.mall;

import com.bonc.medicine.entity.mall.Supply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface SupplyMapper {

    public int insertSupply(Supply tempData); //发布供应

    public List<Map> queryRecommend(String site); // 推荐查询

    public List<Map> querySupplyByCondition(Map<String, Object> tempData); //最新供应列表查询

    public Map queryDetails(String id); //供应详情查询

    public List<Map> queryOtherSupply(@Param("sellerID") int sellerID, @Param("ID") int ID, @Param("NUM") int NUM); //其它供应列表查询

    public int deleteData(int ID); //删除数据(逻辑删除，改变status的值)   删除下架   删除未审核的供应

    public List<Map> queryCheckedSupply(int sellerID); //查询审核状态的供应

    public List<Map> queryMark(int sellerID); //查询给我的留言
    
    public List<Map> inReview(int sellerID);

    public List<Map> againSupply(Supply supply);

    public List<Map> querySupplyById(int id);

    public List<Map> myOffer(int id);

    public List<Map> queryOffer(int id); 

}
