package com.bonc.medicine.service.mall;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.mall.Supply;


public interface SupplyService {
    public Result<Object> releaseSupply(Supply tempData); // 发布供应

    public Result<Object> getRecommend(String site); // 精品推荐查询

    public List<Map<String, Object>> getLastest(JSONObject json); // 最新供应列表查询

    public Result<Object> getDetails(String id); //供应详情查询

    public Result<Object> getOwnSupply(int sellerID); //自己的供应列表查询

    public Result<Object> getOtherSupply(int sellerID, int ID, int NUM); //自己其它供应列表查询

    public Result<Object> getXiaJia(int sellerID); //下架供应查询

    public Result<Object> deleteXiaJia(int ID); //删除下架

    public Result<Object> getCheckedSupply(int sellerID); //查询审核的供应

    public Result<Object> deleteUncheckedSupply(int ID); // 删除未审核的供应

    public Result<Object> getMarks(int sellerID); // 查询给我的留言
    
    public Result<Object> inReview(int sellerID); 
    
    public Result<Object> againSupply(Supply supply);
    
    public Result<Object> querySupplyById(int id);

    public Result<Object> myOffer(int id);

    public Result<Object> queryOffer(int id);
}
