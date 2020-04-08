package com.example.log_orm;

import org.springframework.context.ApplicationEvent;

/**
 * @author yanlianglong
 * @Title: LogEvent.java
 * @Package com.example.log_orm
 * @Description:
 * @date 2020/4/8 10:28
 */
public class LogEvent extends ApplicationEvent {
    public LogEvent(Log source) {
        super(source);
    }
}
