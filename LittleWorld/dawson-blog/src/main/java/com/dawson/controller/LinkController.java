package com.dawson.controller;


import com.dawson.annotation.SystemLog;
import com.dawson.domain.ResponseResult;
import com.dawson.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
@Api(tags = "友链")
public class LinkController {


    @Autowired
    private LinkService linkService;

    @GetMapping("/getAllLink")
    @ApiOperation(value = "获取所有友链")
//    @SystemLog(businessName = "获取所有的友链")
    public ResponseResult getAllLink(){
          return linkService.getAllLink();
    }
}
