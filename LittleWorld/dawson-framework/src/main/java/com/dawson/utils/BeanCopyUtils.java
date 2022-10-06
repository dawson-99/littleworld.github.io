package com.dawson.utils;


import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class BeanCopyUtils {


     //转换单个Bean
     public static  <T> T copyBean(Object source, Class<T> clazz) {
         T res = null;
         try {
             res = clazz.newInstance();
             BeanUtils.copyProperties(source, res);
         } catch (InstantiationException e) {
             e.printStackTrace();
         } catch (IllegalAccessException e) {
             e.printStackTrace();
         }
         return res;
     }

     //转换list类型的Bean
    public static <R,T> List<T> copyListBean(List<R> resource,  Class<T> clazz){
        return resource.stream().map(p -> {
            return copyBean(p, clazz);
        }).collect(Collectors.toList());
    }


}
