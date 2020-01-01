package com.nsusoft.management.controller;

import com.nsusoft.management.domain.SysLog;
import com.nsusoft.management.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {
    //访问的开始时间
    private Date visitTime;
    //访问的类
    private Class clazz;
    //访问的方法
    private Method method;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysLogService service;

    //前置通知  主要获取开始时间，执行的类，执行的方法
    @Before("execution(* com.nsusoft.management.controller.*.*(..))")
    public void doBefor(JoinPoint joinPoint) throws NoSuchMethodException {
        //当前时间就是开始的时间
        visitTime = new Date();
        //访问的类
        clazz = joinPoint.getTarget().getClass();

        //获取方法名
        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();//获取有参方法的参数
        if (args == null || args.length == 0)
            method = clazz.getMethod(methodName);//只能获取无参方法
        else {
            //获取有参的具体方法
            Class[] classArgs = new Class[args.length];
            for (int i = 0; i < classArgs.length; i++)
                classArgs[i] = args[i].getClass();
            clazz.getMethod(methodName, classArgs);
        }
    }

    //后置通知
    @After("execution(* com.nsusoft.management.controller.*.*(..))")
    public void doAfter(JoinPoint joinPoint) {
        //获取执行时长
        long executionTime = (new Date().getTime()) - visitTime.getTime();
        //获取URL
        String url = null;

        //通过反射获取URL
        if (clazz != null && method != null && clazz != LogAop.class) {
            //获取类上的RequestMapping
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if (classAnnotation != null) {
                String[] classValue = classAnnotation.value();

                //获取方法上的RequestMapping
                RequestMapping methodAnnotation = (RequestMapping) method.getAnnotation(RequestMapping.class);
                if (methodAnnotation != null) {
                    String[] methodValue = methodAnnotation.value();
                    url = classValue[0] + methodValue[0];
                }
            }
        }

        //获取访问的IP
        String ip = request.getRemoteAddr();

        //获取访问的用户的用户名
        SecurityContext context = SecurityContextHolder.getContext();
        User user = (User) context.getAuthentication().getPrincipal();
        String username = user.getUsername();

        SysLog sysLog = new SysLog();
        sysLog.setVisitTime(visitTime);
        sysLog.setUsername(username);
        sysLog.setIp(ip);
        sysLog.setUrl(url);
        sysLog.setExecutionTime(executionTime);
        sysLog.setMethod("[类名] " + clazz.getName());

        //将日志写入数据库
        service.save(sysLog);
    }
}