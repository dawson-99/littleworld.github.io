package com.dawson.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

//VO类就是真正返回到前段的数据类，因为没必要返回太多的信息，或者有些信息是不能够返回的，所以要封装一个类来返回一下
public class HotArticleVo {

    private Long id;

    //标题
    private String title;

    //访问量
    private Long viewCount;


}
