package com.dawson.Controller;


import com.dawson.domain.DTO.TagDto;
import com.dawson.domain.DTO.TagListDto;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.vo.PageVo;
import com.dawson.domain.vo.TagVo;
import com.dawson.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
@Api(tags = "标签类")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("/list")
    @ApiOperation(value = "查询标签")
    public ResponseResult<PageVo> tagList(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    @PostMapping
    @ApiOperation(value = "添加标签")
    public ResponseResult addTag(@RequestBody TagDto tagDto){
        return tagService.addTag(tagDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除标签")
    public ResponseResult deleteTag(@PathVariable("id") Long id){
        return tagService.deleteTag(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "通过id去查询标签")
    public ResponseResult showTag(@PathVariable("id") Long id){
        return tagService.showTag(id);
    }

    @PutMapping
    @ApiOperation(value = "修改标签")
    public ResponseResult alterTag(@RequestBody TagVo tagVo){
        return tagService.alterTag(tagVo);
    }

    @GetMapping("/listAllTag")
    @ApiOperation(value = "查询所有的标签")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }


}
