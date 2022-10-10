package com.dawson.controller;


import com.dawson.annotation.SystemLog;
import com.dawson.domain.ResponseResult;
import com.dawson.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Autowired
    UploadService uploadService;


    @PostMapping("/upload")
//    @SystemLog(businessName = "上传头像")
    public ResponseResult upload(MultipartFile file){
        return uploadService.upload(file);
    }

}