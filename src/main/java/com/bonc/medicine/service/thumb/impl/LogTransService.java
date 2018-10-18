package com.bonc.medicine.service.thumb.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bonc.medicine.annotation.MethodLog;
import com.bonc.medicine.constants.Constants;
import com.bonc.medicine.entity.user.TokenModel;
import com.bonc.medicine.service.thumb.LogsService;
import com.bonc.medicine.service.user.UserService;

/**
 * 
 * @ClassName: LogService 
 * @Description: 日志的切面实现
 * @author: hejiajun
 * @date: 2018年10月16日 下午4:44:27
 */
@Component
@Aspect
public class LogTransService {
	
	@Autowired
    private LogsService logsService;

	 @Autowired
	 private UserService userService;
	 
    public LogTransService() {
    }

    /**
     * 切点
     */
    @Pointcut("@annotation(com.bonc.medicine.annotation.MethodLog)")
    public void methodCachePointcut() {
    }
    
    @Around("methodCachePointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
        		.getRequestAttributes()).getRequest();
        //String ip = getIp(request);
        
        String userId = getUserId(request);
        
        String methodRemark = getMthodRemark(point);
        
        Object[] method_param;

        Object object;
        try {
            method_param = point.getArgs(); //获取方法参数
            // String param=(String) point.proceed(point.getArgs());
            object = point.proceed();
        } catch (Exception e) {
            // 异常处理记录日志..log.error(e);
            throw e;
        }
        
        String [] opInfo = methodRemark.split(",");
        //userId;opeType;opeResource;opModule;status
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("opeType", opInfo.length >= 1 ? opInfo[0] : "");
        paramMap.put("opeResource", opInfo.length >= 2 ? opInfo[1] : "");
        paramMap.put("opModule", opInfo.length >= 3 ? opInfo[2] : "");
        logsService.addOperLogs(paramMap);
        return object;

    }

    /**
     * 获取请求ip
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip.substring(0, index);
            } else {
                return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }

    /**
     * 获取方法中的中文备注
     *
     * @param joinPoint
     * @return
     * @throws Exception
     */
    public static String getMthodRemark(ProceedingJoinPoint joinPoint) throws Exception {

        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        Class targetClass = Class.forName(targetName);
        Method[] method = targetClass.getMethods();
        String methode = "";
        for (Method m : method) {
            if (m.getName().equals(methodName)) {
                Class[] tmpCs = m.getParameterTypes();
                if (tmpCs.length == arguments.length) {
                    MethodLog methodCache = m.getAnnotation(MethodLog.class);
                    if (methodCache != null) {
                        methode = methodCache.remark();
                    }
                    break;
                }
            }
        }
        return methode;
    }
    
    private String getUserId(HttpServletRequest request) throws Exception{
    	 String authorization =  request.getHeader(Constants.AUTHORIZATION);
         //Long currentUserId = (Long) webRequest.getAttribute(Constants.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);
         TokenModel user = userService.getToken(authorization);
         if (user != null) {
             //从数据库中查询并返回
             return String.valueOf(user.getUserId());
         } else {
             return "";
         }
	}
}
