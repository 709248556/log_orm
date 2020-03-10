package com.example.log_orm;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanlianglong
 * @Title: UserController.java
 * @Package com.example.log_orm
 * @Description:
 * @date 2020/3/10 10:52
 */
@RestController
public class UserController {
    /**
     *  进行aop日志记录，也可以放在service上。
     */
    @LogAnno(operateType = "添加用户")
    @RequestMapping(value = "/user/add")
    public String add() {
        return "向数据库中添加用户!!";
    }
}
