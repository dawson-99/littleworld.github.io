package com.dawson.Controller;


import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.Link;
import com.dawson.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
@Api(tags = "友链接口")
public class LinkController {

    @Autowired
    LinkService linkService;


    /**
     * 相似的友链查询
     */
    @GetMapping("/list")
    @ApiOperation(value = "相似的友链查询")
    public ResponseResult list(Long pageNum, Long pageSize, String name, String status){
        return linkService.LinkList(pageNum, pageSize, name, status);
    }

    /**
     * 增加友链
     */
    @PostMapping
    @ApiOperation(value = "添加友链")
    public ResponseResult addLink(@RequestBody Link link){
        linkService.save(link);
        return ResponseResult.okResult();
    }

    /**
     * 查询友链
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "选取友链")
    public ResponseResult selectLink(@PathVariable("id") Long id){
        Link link = linkService.getById(id);
        return ResponseResult.okResult(link);
    }

    /**
     * 修改友链
     */
    @PutMapping
    @ApiOperation(value = "修改友链")
    public ResponseResult alterLink(@RequestBody Link link){
        linkService.updateById(link);
        return ResponseResult.okResult();
    }

    /**
     * 删除友链
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除友链")
    public ResponseResult deleteLink(@PathVariable("id") Long id){
        linkService.removeById(id);
        return ResponseResult.okResult();
    }
}
