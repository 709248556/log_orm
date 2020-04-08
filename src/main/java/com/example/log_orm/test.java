package com.example.log_orm;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author yanlianglong
 * @Title: test.java
 * @Package com.example.log_orm
 * @Description:
 * @date 2020/3/11 17:56
 */
public class test {

    public static void main(String[] args) {

        List<Log> logs1 = new ArrayList<>();
        List<Log> logs2 = new ArrayList<>();
        for(int i = 0;i<10;i++){
            Log log1 = new Log();
            log1.setId(i);
            log1.setIp("id:"+i);
            logs1.add(log1);
            Log log2 = new Log();
            log2.setId(i+8);
            log2.setIp("id:"+(i+8));
            logs2.add(log2);
        }
        try {
            // 根据name,sex两个属性去重
            List<Log> unique = logs2.stream().collect(
                    Collectors. collectingAndThen(
                            Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getId() + ";" + o.getIp()))), ArrayList::new)
            );
            System.out.println(unique);
        }catch (Exception e){

        }


    }
}
