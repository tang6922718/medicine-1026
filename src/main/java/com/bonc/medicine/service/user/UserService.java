package com.bonc.medicine.service.user;


import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.user.TokenModel;
import com.bonc.medicine.entity.user.User;

import java.util.List;
import java.util.Map;

/**
* @Description:用户登陆注册密码等接口
* @Author: hejiajun
* @Date: 2018/9/1
*/

public interface UserService {

    /**
     * @Description:注册-->完善用户的基本信息
     * @return: int
     * @Author: hejiajun
     * @Date: 2018/8/31
     */
    public int completeUserInfo(User user);

    /**
     * @Description:用户注册功能，包括--用户基本信息，关心品种，角色
     * @return: int
     * @Author: hejiajun
     * @Date: 2018/8/31
     */
    public int signUp(Map<String, String> paramMap);

    /**
     * @Description:注册-->完善用户角色信息
     * @return: int
     * @Author: hejiajun
     * @Date: 2018/8/31
     */
    public int signUpUserRoleRelation();

    /**
     * @Description:注册-->完善用户关心品种信息
     * @return: int
     * @Author: hejiajun
     * @Date: 2018/8/31
     */
    public int signUpCareVarieties();

    public int updatePassword(Map<String, String> paramMap);

    /**
     * @Description:通过电话号码找出电话号码所在的表的名称，来判断。当前修改密码的用户是什么用户
     * @Param: [phone]
     * @return: java.lang.String
     * @Author: hejiajun
     * @Date: 2018/9/1
     */
    public String getTableByPhone(String phone);

    public Result login(String username, String password) throws Exception;

    public Result loginSecond(String username, String password, String equipment) throws Exception;

    public Result getUserByToken(String token) throws  Exception;

    public TokenModel createToken(User user) throws Exception;

    public TokenModel getToken(String authentication) throws Exception;

    public boolean checkToken(TokenModel model) throws Exception;

    public void deleteToken(String token) throws Exception;

    public Map changePassword(Map<String, String> map);

    public User getUserInfoById(String userId);

    public List<Map<String, Object>> interfaceForBackAfterLogin(String userId) throws  Exception;




}
