package com.dawson.controller;


import com.dawson.annotation.SystemLog;
import com.dawson.domain.ResponseResult;
import com.dawson.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "上传图片")
public class UploadController {

    @Autowired
    UploadService uploadService;


    @PostMapping("/upload")
    @ApiOperation(value = "上传头像，图片")
//    @SystemLog(businessName = "上传头像")
    public ResponseResult upload(MultipartFile file){
        return uploadService.upload(file);
    }

}
