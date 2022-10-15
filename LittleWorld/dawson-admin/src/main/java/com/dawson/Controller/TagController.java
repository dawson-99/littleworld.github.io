package com.dawson.Controller;


import com.dawson.domain.DTO.TagDto;
import com.dawson.domain.DTO.TagListDto;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.vo.PageVo;
import com.dawson.domain.vo.TagVo;
import com.dawson.service.TagService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> tagList(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    @PostMapping
    public ResponseResult addTag(@RequestBody TagDto tagDto){
        return tagService.addTag(tagDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable("id") Long id){
        return tagService.deleteTag(id);
    }

    @GetMapping("/{id}")
    public ResponseResult showTag(@PathVariable("id") Long id){
        return tagService.showTag(id);
    }

    @PutMapping
    public ResponseResult alterTag(@RequestBody TagVo tagVo){
        return tagService.alterTag(tagVo);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }


}
