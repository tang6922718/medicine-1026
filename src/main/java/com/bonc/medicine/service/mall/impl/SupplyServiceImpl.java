package com.bonc.medicine.service.mall.impl;

import com.alibaba.fastjson.JSONObject;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.mall.Supply;
import com.bonc.medicine.mapper.mall.SupplyMapper;
import com.bonc.medicine.service.mall.SupplyService;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class SupplyServiceImpl implements SupplyService {

    @Autowired
    private SupplyMapper supplyMapper;  // 相当于DAO工具类

    @Override
    public Result<Object> releaseSupply(Supply tempData) {
        tempData.setPublic_time(new Date()); // 时间暂时以后台为准
        return ResultUtil.success(supplyMapper.insertSupply(tempData));
    }


    /* *
     * @Description 推荐查询
     * @Date 15:22 2018/9/2
     * @Param [site]    0 全部  1  app精品推荐   2 门户源头好货推荐   3 门户精品商家
     * @return com.bonc.mall.entity.Result<java.lang.Object>
     */
    @Override
    public Result<Object> getRecommend(String site) {
        return ResultUtil.success(supplyMapper.queryRecommend(site));
    }


    /* *
     * @Description 最新供应列表查询
     * @Date 16:36 2018/8/23
     * @Param [pageNumber, pageSize]
     * @return com.bonc.mall.entity.Result<java.lang.Object>
     */
    @Override
    public Result<Object> getLastest(JSONObject json) {
        Map<String,Object> tempData=new HashMap<String, Object>();
        tempData.putAll(json);

        tempData.put("is_audit","1"); // 0未审核，1审核通过，2审核未通过

        tempData.put("carriage_status","1"); // 0，未上架；1，上架；2，下架

//        PageHelper.startPage(pageNumber, pageSize);
        List<Map> mapList=supplyMapper.querySupplyByCondition(tempData);
        return ResultUtil.success(mapList);
    }

    /* *
 * @Description 自己的供应列表查询
 * @Date 11:50 2018/8/23
 * @Param [SellerID]
 * @return com.bonc.mall.entity.Result<java.lang.Object>
 */
    @Override
    public Result<Object> getOwnSupply(int sellerID) {
        Map<String,Object> tempData=new HashMap<String, Object>();

        tempData.put("seller_id",String.valueOf(sellerID));

        tempData.put("is_audit","1"); // 0未审核，1审核通过，2审核未通过

        tempData.put("carriage_status","1"); // 0，未上架；1，上架；2，下架

        return ResultUtil.success(supplyMapper.querySupplyByCondition(tempData));
    }

    /* *
     * @Description 供应详情查询
     * @Date 19:34 2018/8/22
     * @Param [id]
     * @return com.bonc.mall.entity.Result<java.lang.Object>
     */
    @Override
    public Result<Object> getDetails(String id) {
        return ResultUtil.success(supplyMapper.queryDetails(id));
    }


    /* *
     * @Description 自己其它供应列表查询
     * @Date 16:35 2018/8/23
     * @Param [SellerID, ID, NUM]
     * @return com.bonc.mall.entity.Result<java.lang.Object>
     */
    @Override
    public Result<Object> getOtherSupply(int sellerID, int ID, int NUM) {
        if("".equals(NUM+"")){ // 默认查多少条  可根据需要修改
            NUM=4;
        }
        return ResultUtil.success(supplyMapper.queryOtherSupply(sellerID,ID,NUM));
    }

    /* *
     * @Description 下架供应查询
     * @Date 18:09 2018/8/23
     * @Param [sellerID]
     * @return com.bonc.mall.entity.Result<java.lang.Object>
     */
    @Override
    public Result<Object> getXiaJia(int sellerID) {
        Map<String,Object> tempData=new HashMap<String, Object>();

        tempData.put("seller_id",String.valueOf(sellerID));

        tempData.put("is_audit","1"); // 0未审核，1审核通过，2审核未通过

        tempData.put("carriage_status","2"); // 0，未上架；1，上架；2，下架

        return ResultUtil.success(supplyMapper.querySupplyByCondition(tempData));
    }

    /* *
     * @Description 删除下架
     * @Date 20:03 2018/8/23
     * @Param [ID]
     * @return com.bonc.mall.entity.Result<java.lang.Object>
     */
    @Override
    public Result<Object> deleteXiaJia(int ID) {
        return ResultUtil.success(supplyMapper.deleteData(ID));
    }


    /* *
     * @Description 查询审核的供应
     * @Date 10:05 2018/8/24
     * @Param [sellerID]
     * @return com.bonc.mall.entity.Result<java.lang.Object>
     */
    @Override
    public Result<Object> getCheckedSupply(int sellerID) {
        return ResultUtil.success(supplyMapper.queryCheckedSupply(sellerID));
    }


    @Override
    public Result<Object> deleteUncheckedSupply(int ID) {
        return ResultUtil.success(supplyMapper.deleteData(ID));
    }


    /* *
     * @Description 查询给我的留言
     * @Date 14:32 2018/8/24
     * @Param [sellerID]
     * @return com.bonc.mall.entity.Result<java.lang.Object>
     */
    @Override
    public Result<Object> getMarks(int sellerID) {

        return null;
    }

    @Override
    public Result<Object> inReview(int sellerID) {
        return ResultUtil.success(supplyMapper.inReview(sellerID));
    }
    
    @Override
    public Result<Object> againSupply(Supply supply) {
    	return ResultUtil.success(supplyMapper.againSupply(supply));
    }
    
    @Override
    public Result<Object> querySupplyById(int id) {
    	return ResultUtil.success(supplyMapper.querySupplyById(id));
    }

    @Override
    public Result<Object> myOffer(int id) {
        return ResultUtil.success(supplyMapper.myOffer(id));
    }

    @Override
    public Result<Object> queryOffer(int id) {
        return ResultUtil.success(supplyMapper.queryOffer(id));
    }
    
}
