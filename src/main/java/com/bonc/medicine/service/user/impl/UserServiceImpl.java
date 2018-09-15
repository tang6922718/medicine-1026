package com.bonc.medicine.service.user.impl;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.user.TokenModel;
import com.bonc.medicine.entity.user.User;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.mapper.user.UserMapper;
import com.bonc.medicine.service.RedisService;
import com.bonc.medicine.service.user.UserService;
import com.bonc.medicine.utils.JsonUtil;
import com.bonc.medicine.utils.RedisKeyUtil;
import com.bonc.medicine.utils.ResultUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Repository
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private int OUT_TIME = 30000;

	@Autowired
	private RedisService redisService;

	@Autowired
	private UserMapper userMapper;

    @Override
    public int completeUserInfo(User user) {
        return 0;
    }

    @Override
	public int signUp(Map<String, String> paramMap) {

        //校验用户注册的手机号
        if (StringUtils.isEmpty(paramMap.get("phone").trim()) ||
                StringUtils.isEmpty(paramMap.get("password").trim())){
            throw new MedicineRuntimeException(ResultEnum.MISSING_PARA);
        }

        //判断手机号是否已经被注册先直接查询数据库，还没想好redis的key怎么设置
        String phone = paramMap.get("phone").trim();
        List<Map<String, Object>> userByPhone = userMapper.getIdByPhone(phone);
        if (userByPhone.size() > 0 && null !=userByPhone.get(0).get("id")
            &&!StringUtils.isEmpty(userByPhone.get(0).get("id").toString())){

            throw new MedicineRuntimeException(ResultEnum.UN_USE_PHONE);
        }

        paramMap.put("password", DigestUtils.md5Hex(paramMap.get("password")));

        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        paramMap.put("updateTime" , df.format(new Date()));

        // TODO 是否校验密码
        userMapper.signUp(paramMap);

		return 1;
	}

    @Override
    public int signUpUserRoleRelation() {
        return 0;
    }

    @Override
    public int signUpCareVarieties() {
        return 0;
    }

    @Override
    public int updatePassword(Map<String, String> paramMap) {

        paramMap.put("tableName", getTableByPhone(paramMap.get("phone")));
        paramMap.put("password", DigestUtils.md5Hex(paramMap.get("password")));

        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        paramMap.put("updateTime" , df.format(new Date()));

        return userMapper.updatePassword(paramMap);
    }


    @Override
    public String getTableByPhone(String phone) {
        List<Map<String, Object>> tableMap = userMapper.getTableByPhone(phone);
        if (tableMap.size() == 0 || tableMap.get(0).get("table_name") == null ){
            throw new MedicineRuntimeException(ResultEnum.ERROR_PHONE);
        }

        return tableMap.get(0).get("table_name").toString();

    }

    public Result login(String username, String password) throws Exception{

        Map paramMap = new HashMap();
        paramMap.put("tableName", getTableByPhone(username));
        paramMap.put("phone", username);
        paramMap.put("password", DigestUtils.md5Hex(password));
        List<Map<String, Object>> reList = userMapper.login(paramMap);
		if (reList.size() == 0 || reList.get(0).get("telephone") == null) {
			return ResultUtil.error(ResultEnum.ERROR_LOGIN);
		}
		User user = new User();
        user.setId(reList.get(0).get("id") == null ? 0 : Integer.parseInt(reList.get(0).get("id") + ""));
        user.setName(reList.get(0).get("name") == null ? "" : reList.get(0).get("name").toString());
        user.setTelephone(reList.get(0).get("telephone") == null ? "" : reList.get(0).get("telephone").toString());
        user.setHeadPortrait(reList.get(0).get("head_portrait") == null ? "" : reList.get(0).get("head_portrait").toString());
        user.setAddress(reList.get(0).get("address") == null ? "" : reList.get(0).get("address").toString());

        //String token = UUID.randomUUID().toString();
        String token = UUID.randomUUID().toString().replace("-", "");
		// 3、把用户信息保存到redis。Key就是token，value就是User对象转换成json。
		// 4、使用String类型保存Session信息。可以使用“前缀:token”为key
		//user.setPassword(null);
        redisService.set(RedisKeyUtil.getUserInfoKey(token), JsonUtil.getJsonString(user));
		// 5、设置key的过期时间。模拟Session的过期时间。一般半个小时。
        redisService.expire(RedisKeyUtil.getUserInfoKey(token), OUT_TIME);
		// 6、返回Result包装token。
        Map map = new HashMap();
        map.put("token", token);
        map.put("userId", user.getId());
		return ResultUtil.success(map);
	}

    public Result getUserByToken(String token) throws  Exception{
        // 2、根据token查询redis。
        String json = redisService.get("USER_INFO" + ":" + token);
        if (StringUtils.isBlank(json)) {
            // 3、如果查询不到数据。返回用户已经过期。
            return ResultUtil.error(400, "用户登录已经过期，请重新登录。");
        }
        // 4、如果查询到数据，说明用户已经登录。
        // 5、需要重置key的过期时间。
        redisService.expire("USER_INFO" + ":" + token, OUT_TIME);
        // 6、把json数据转换成User对象，然后使用Result包装并返回。
        User user = JsonUtil.json2Obj(json, User.class);
        return ResultUtil.success(user);
    }

    @Override
    public TokenModel createToken(User user) throws Exception {
        //使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        TokenModel model = new TokenModel(user.getId(), token);
        //存储到redis并设置过期时间
        //redis.boundValueOps(userId).set(token, Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        //return model;
        //String token = UUID.randomUUID().toString();
        // 3、把用户信息保存到redis。Key就是token，value就是User对象转换成json。
        // 4、使用String类型保存Session信息。可以使用“前缀:token”为key
        //user.setPassword(null);
        redisService.set(RedisKeyUtil.getUserInfoKey(token), JsonUtil.getJsonString(user));
        // 5、设置key的过期时间。模拟Session的过期时间。一般半个小时。
        redisService.expire(RedisKeyUtil.getUserInfoKey(token), OUT_TIME);

        return model;
    }

    @Override
    public boolean checkToken(TokenModel model) throws Exception {
        if (model == null) {
            return false;
        }
        String json = redisService.get(RedisKeyUtil.getUserInfoKey(model.getToken()));
        //String token = redis.boundValueOps(model.getUserId()).get();
        if (StringUtils.isEmpty(json)) {
            return false;
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        redisService.expire(RedisKeyUtil.getUserInfoKey(model.getToken()), OUT_TIME);
        // redis.boundValueOps(model.getUserId()).expire(Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return true;
    }

    public TokenModel getToken(String authentication) {
        if (authentication == null || authentication.length() == 0) {
            return null;
        }
        String[] param = authentication.split("_");
        if (param.length != 2) {
            return null;
        }
        //使用userId和源token简单拼接成的token，可以增加加密措施
        long userId = Long.parseLong(param[0]);
        String token = param[1];
        return new TokenModel(userId, token);
    }

    public void deleteToken(String token) throws Exception {
        redisService.del(RedisKeyUtil.getUserInfoKey(token));
    }

    @Override
    public Map changePassword(Map<String, String> map) {
        //keys:oldPassword;newPassword,secNewPassword,telephone
        if (StringUtils.isEmpty(map.get("oldPassword")) || StringUtils.isEmpty(map.get("newPassword")) ||
                StringUtils.isEmpty(map.get("secNewPassword"))){
            throw new MedicineRuntimeException(ResultEnum.MISSING_PARA);
        }

        if(!StringUtils.equals(map.get("newPassword"), map.get("secNewPassword"))){
            throw new MedicineRuntimeException(ResultEnum.PERMISSION);
        }

        Map paramMap = new HashMap();
        paramMap.put("tableName", getTableByPhone(map.get("telephone")));
        paramMap.put("phone", map.get("telephone"));
        paramMap.put("password", DigestUtils.md5Hex(map.get("oldPassword")));
        List<Map<String, Object>> reList = userMapper.login(paramMap);

        if (reList.size() == 0 || reList.get(0).get("telephone") == null) {
            throw  new MedicineRuntimeException(ResultEnum.MISSING_PARA);
        }
        paramMap.put("password", DigestUtils.md5Hex(map.get("secNewPassword")));
        int rows =  updatePassword(paramMap);

        Map<String, Object> reMap = new HashMap<>();

        if (rows < 1 ){
            throw  new MedicineRuntimeException(ResultEnum.NET_ERROR);
        }
        reMap.put("succeed", 1);


        return reMap;
    }


}
