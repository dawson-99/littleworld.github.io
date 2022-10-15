package com.dawson.service.impl;

import com.dawson.domain.ResponseResult;
import com.dawson.enums.AppHttpCodeEnum;
import com.dawson.exception.SystemException;
import com.dawson.service.UploadService;
import com.dawson.utils.PathUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
@Data
@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String prefix;

    @Override
    public ResponseResult upload(MultipartFile file) {

        // TODO 文件大小类型的判断

        if(!(file.getOriginalFilename().endsWith(".jpg") || file.getOriginalFilename().endsWith(".png") ||
           file.getOriginalFilename().endsWith(".jpeg"))){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        String originalFilename = file.getOriginalFilename();
        //用工具类进行判断
        String path =PathUtils.generateFilePath(originalFilename);


        String finalUrl = uploadAvator(file, path);
        return ResponseResult.okResult(finalUrl);
    }

    @Override
    public ResponseResult uploadImg(MultipartFile multipartFile) {
        return upload(multipartFile);
    }

    private String uploadAvator(MultipartFile file, String path){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = path;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(file.getInputStream(),key,upToken,null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return prefix + path;
    }
}
