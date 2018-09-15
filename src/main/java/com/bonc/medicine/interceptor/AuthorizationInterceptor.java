package com.bonc.medicine.interceptor;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.annotation.Authorization;
import com.bonc.medicine.constants.Constants;
import com.bonc.medicine.entity.user.TokenModel;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @program: medicine-hn
 * @description:
 * @author: hejiajun
 * @create: 2018-09-10 17:24
 **/
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //从header中得到token
        String authorization = request.getHeader(Constants.AUTHORIZATION);

        //验证token
        TokenModel model = userService.getToken(authorization);
        if (userService.checkToken(model)) {
            //如果token验证成功，将token对应的用户id存在request中，便于之后注入
            request.setAttribute(Constants.CURRENT_USER_ID, model.getUserId());
            return true;
        }

        //如果验证token失败，并且方法注明了Authorization，返回401错误
        if (method.getAnnotation(Authorization.class) != null) {
            throw new MedicineRuntimeException(ResultEnum.MISSING_PARA);
        }

        return true;
    }
}
