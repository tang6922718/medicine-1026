package com.bonc.medicine.service.user.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.mapper.user.RoleManagerMapper;
import com.bonc.medicine.service.user.RoleManagerService;
import com.github.pagehelper.PageHelper;

/**
 * @program: medicine
 * @description:
 * @author: hejiajun
 * @create: 2018-09-15 21:50
 **/
@Repository
public class RoleManagerServiceImpl implements RoleManagerService {

	private String key = "succeed";

	@Autowired
	private RoleManagerMapper roleManagerMapper;

	@Override
	@Transactional
	public Map<String, Object> createNewRole(Map<String, String> map) {

		int row = roleManagerMapper.createNewRole(map);
		if (row < 1) {
			throw new MedicineRuntimeException(ResultEnum.NET_ERROR);
		}

		Map reMap = new HashMap();
		reMap.put(key, row);

		return reMap;
	}

	@Override
	public List<Map<String, Object>> getRolesByCondition(Map<String, String> map) {

		PageHelper.startPage(Integer.parseInt(map.get("pageIndex")), Integer.parseInt(map.get("pageSize")));
		List<Map<String, Object>> reList = roleManagerMapper.getRolesByCondition(map);

		if (null == reList || reList.size() < 1) {
			throw new MedicineRuntimeException(ResultEnum.NO_CONTENT);
		}

		for (Map<String, Object> inMap : reList) {
			if (null != inMap.get("create_time")) {
				SimpleDateFormat adf = new SimpleDateFormat("yyyy-MM-dd");

				// Date dd = new Date(Long.parseLong(inMap.get("create_time") +
				// "") * 1000);
				// String sec = inMap.get("create_time") + "";

				inMap.put("create_time", adf.format(inMap.get("create_time")));
			}
		}
		return reList;
	}

	@Override
	@Transactional
	public Map<String, Object> removeRole(String id) {

		int row = roleManagerMapper.removeRole(id);
		if (row < 1) {
			throw new MedicineRuntimeException(ResultEnum.NET_ERROR);
		}
		if (row > 1) {
			throw new MedicineRuntimeException(ResultEnum.ERROE);
		}

		Map reMap = new HashMap();
		reMap.put(key, row);

		return reMap;
	}

	@Override
	@Transactional
	public Map<String, Object> updateRoleInfo(Map<String, String> map) {

		int row = roleManagerMapper.updateRoleInfo(map);

		if (row < 1) {
			throw new MedicineRuntimeException(ResultEnum.NET_ERROR);
		}
		if (row > 1) {
			throw new MedicineRuntimeException(ResultEnum.ERROE);
		}
		Map reMap = new HashMap();
		reMap.put(key, row);

		return reMap;
	}

	/**
	 * 
	 * @Title: updateRolePermissions
	 * @Description: map:{ "roleId":"123", "menuIds":"12,13,14,15" }
	 * @param putParam
	 * @return
	 * @see com.bonc.medicine.service.user.RoleManagerService#updateRolePermissions(java.util.Map)
	 */
	@Override
	@Transactional
	public Map<String, Object> updateRolePermissions(Map<String, String> putParam) {

		if (StringUtils.isBlank(putParam.get("menuIds"))) {
			throw new MedicineRuntimeException(ResultEnum.MISSING_PARA);
		}

		String[] menuIds = putParam.get("menuIds").split(",");
		if (menuIds.length < 1) {
			throw new MedicineRuntimeException(ResultEnum.MISSING_PARA);
		}

		List<Map<String, String>> idMap = new ArrayList<>();
		for (String menuId : menuIds) {
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("roleId", putParam.get("roleId"));
			paramMap.put("menuId", menuId);
			idMap.add(paramMap);
		}

		Map<String, Object> nowNumber = roleManagerMapper.queryRolesMenuNumber(putParam.get("roleId"));
		int deletedRow = roleManagerMapper.deleteRoleMeunRelation(putParam.get("roleId"));

		if (!StringUtils.equals(nowNumber.get("menuNumber") + "", deletedRow + "")) {
			throw new MedicineRuntimeException(ResultEnum.NET_ERROR);
		}
		int updatedRows = roleManagerMapper.updateRolePermissions(idMap);
		if (updatedRows != idMap.size()) {
			throw new MedicineRuntimeException(ResultEnum.NET_ERROR);
		}

		return new HashMap<>();
	}

	public List<Map<String, Object>> queryRoleMenu(String roleId) {

		List<Map<String, Object>> meunList = roleManagerMapper.queryRoleMenu(roleId);
		if (null == meunList || meunList.size() < 1 || null == meunList.get(0)) {
			return new ArrayList<>();
		}

		return convertReturnData(meunList);
	}

	/*
	 * public static void main(String[] args) throws Exception{ List<Map<String,
	 * Object>> reList = new ArrayList<>();
	 * 
	 * for (int i = 0; i < 10; i++){ Map map = new HashMap();
	 * map.put("create_time", "1537150610"); reList.add(map);
	 * 
	 * } System.out.println(reList.toString());
	 * 
	 * for (Map<String, Object> inMap: reList) { if(null !=
	 * inMap.get("create_time")){ SimpleDateFormat adf = new
	 * SimpleDateFormat("yyyy-MM-dd");
	 * 
	 * Date dd = new Date(Long.parseLong(inMap.get("create_time") + "") * 1000);
	 * String sec = inMap.get("create_time") + ""; inMap.put("create_time",
	 * TimeFormatUtils.secendsToDate(sec)); } }
	 * System.out.println(reList.toString()); }
	 */

	@SuppressWarnings("Duplicates")
	public List<Map<String, Object>> queryAllMenu() {

		List<Map<String, Object>> meunList = roleManagerMapper.queryAllMenu();

		if (null == meunList || meunList.size() < 1 || null == meunList.get(0)) {
			throw new MedicineRuntimeException(ResultEnum.NO_CONTENT);
		}

		return convertReturnData(meunList);
	}
   
	/**
	 * 
	 * @Title: convertReturnData 
	 * @Description: 转换数据格式
	 * @param meunList
	 * @return
	 * @return: List<Map<String,Object>>
	 */
	public List<Map<String, Object>> convertReturnData(List<Map<String, Object>> meunList) {
		List<Map<String, Object>> reList = new ArrayList<>();

		String parentId = "0";
		String parentName = null;
		String parentUrl = null;
		Map<String, Object> reMap = new HashMap<>();
		List<Map<String, String>> sonMeunList = new ArrayList<>();
		for (int i = 0; i < meunList.size(); i++) {
			Map<String, Object> meunMap = meunList.get(i);
			// parentId = meunMap.get("parent_id") + "";
			// parentName = meunMap.get("parent_menu_name") + "";
			// parentUrl = meunMap.get("parent_url") + "";

			if (StringUtils.equals(parentId, meunMap.get("parent_id") + "")) {
				reMap.put("parentId", parentId);
				reMap.put("parentName", meunMap.get("parent_menu_name") + "");
				reMap.put("parentUrl", meunMap.get("parent_url") + "");
				reMap.put("icon", meunMap.get("icon") + "");

				Map<String, String> sonMap = new HashMap<>();
				sonMap.put("sonName", meunMap.get("menu_name") + "");
				sonMap.put("sonId", meunMap.get("id") + "");
				sonMap.put("sonUrl", meunMap.get("son_url") + "");

				sonMeunList.add(sonMap);

				reMap.put("son", sonMeunList);
				if(i == reList.size() - 1){
					reList.add(reMap);
				}
			} else {
				
				parentId = meunMap.get("parent_id") + "";
				reMap = new HashMap();
				reMap.put("parentId", parentId);
				reMap.put("parentName", meunMap.get("parent_menu_name") + "");
				reMap.put("parentUrl", meunMap.get("parent_url") + "");
				reMap.put("icon", meunMap.get("icon") + "");

				sonMeunList = new ArrayList<>();

				Map<String, String> sonMap = new HashMap<>();
				sonMap.put("sonName", meunMap.get("menu_name") + "");
				sonMap.put("sonId", meunMap.get("id") + "");
				sonMap.put("sonUrl", meunMap.get("son_url") + "");

				sonMeunList.add(sonMap);

				reMap.put("son", sonMeunList);
				
				reList.add(reMap);
			}
		}
		return reList;
	}
}
