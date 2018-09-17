package com.bonc.medicine.resolvers;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.annotation.CurrentUser;
import com.bonc.medicine.constants.Constants;
import com.bonc.medicine.entity.user.TokenModel;
import com.bonc.medicine.entity.user.User;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.service.user.UserService;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * @program: medicine
 * @description:
 * @author: hejiajun
 * @create: 2018-09-17 20:23
 **/
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //如果参数类型是User并且有CurrentUser注解则支持
        if (parameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //取出鉴权时存入的登录用户Id
        //String authorization = request.getHeader(Constants.AUTHORIZATION);

        String authorization =  webRequest.getHeader(Constants.AUTHORIZATION);
        //Long currentUserId = (Long) webRequest.getAttribute(Constants.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);
        TokenModel user = userService.getToken(authorization);
        if (user != null) {
            //从数据库中查询并返回
            return String.valueOf(user.getUserId());
        }
        throw new MedicineRuntimeException(ResultEnum.OUT_OF_TIME);
    }
}
