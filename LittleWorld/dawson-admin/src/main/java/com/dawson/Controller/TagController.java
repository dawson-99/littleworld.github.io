package com.dawson.Controller;


import com.dawson.domain.ResponseResult;
import com.dawson.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("/list")
    public ResponseResult tagList(){
        return ResponseResult.okResult(tagService.list());
    }
}
