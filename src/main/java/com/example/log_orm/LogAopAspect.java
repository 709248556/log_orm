package com.example.log_orm;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author yanlianglong
 * @Title: LogAopAspect.java
 * @Package com.example.log_orm
 * @Description: ①切面注解得到请求数据 -> ②发布监听事件 -> ③异步监听日志入库
 * @date 2020/3/10 10:54
 */
@Order(3)
@Component
@Aspect
public class LogAopAspect {

    /**
     * 事件发布是由ApplicationContext对象管控的，我们发布事件前需要注入ApplicationContext对象调用publishEvent方法完成事件发布
     **/
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 环绕通知记录日志通过注解匹配到需要增加日志功能的方法
     */
    @Around("@annotation(com.example.log_orm.LogAnno)")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        // 1.方法执行前的处理，相当于前置通知
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        // 获取方法
        Method method = methodSignature.getMethod();
        // 获取方法上面的注解
        LogAnno logAnno = method.getAnnotation(LogAnno.class);
        // 获取操作描述的属性值
        String operateType = logAnno.operateType();

        //操作的url
        HttpServletRequest request = HttpContextUtil.getRequest();
        String requestURI = request.getRequestURI();


        // 创建一个日志对象(准备记录日志)
        Log log = new Log();
        log.setOperateType(operateType);// 操作说明

        // 设置操作人，从session中获取，这里简化了一下，写死了。
        log.setOperateor("lastwhisper");
        String ip = HttpContextUtil.getIpAddress();
        log.setIp(ip);
        Object result = null;
        try {
            // 让代理方法执行
            result = pjp.proceed();
            // 2.相当于后置通知(方法成功执行之后走这里)
            log.setOperateResult("正常");// 设置操作结果
        } catch (SQLException e) {
            // 3.相当于异常通知部分
            log.setOperateResult("失败");// 设置操作结果
        } finally {
            // 4.相当于最终通知
            log.setOperateDate(new Date());// 设置操作日期
//            logMapper.insert(log);// 添加日志记录
            // 发布事件
            applicationContext.publishEvent(new LogEvent(log));
            System.out.println("发布事件成功");
        }
        return result;
    }

}
