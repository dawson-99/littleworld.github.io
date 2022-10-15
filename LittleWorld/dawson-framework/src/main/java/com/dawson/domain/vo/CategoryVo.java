package com.dawson.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo {
    Long id;
    String name;
    String discription;
    String status;
}
