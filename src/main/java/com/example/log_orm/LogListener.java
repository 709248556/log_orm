package com.example.log_orm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author yanlianglong
 * @Title: LogListener.java
 * @Package com.example.log_orm
 * @Description: 注解形式的监听 异步监听日志事件
 * @date 2020/4/8 10:29
 */
@Component
public class LogListener {
    // 省事少写了service
    @Autowired
    private LogDao logMapper;

    @Async
    @Order
    @EventListener(LogEvent.class)
    public void saveSysLog(LogEvent event) {
        System.out.println("异步监听日志事件成功");
        Log sysLog = (Log) event.getSource();
        // 保存日志
        logMapper.insert(sysLog);
        System.out.println("保存日志成功");
    }
}
