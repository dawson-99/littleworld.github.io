package com.dawson.service;

import com.dawson.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    ResponseResult upload(MultipartFile file);

    ResponseResult uploadImg(MultipartFile multipartFile);
}
