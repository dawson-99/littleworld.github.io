package com.dawson.Controller;


import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.Link;
import com.dawson.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    LinkService linkService;


    /**
     * 相似的友链查询
     */
    @GetMapping("/list")
    public ResponseResult list(Long pageNum, Long pageSize, String name, String status){
        return linkService.LinkList(pageNum, pageSize, name, status);
    }

    /**
     * 增加友链
     */
    @PostMapping
    public ResponseResult addLink(@RequestBody Link link){
        linkService.save(link);
        return ResponseResult.okResult();
    }

    /**
     * 查询友链
     */
    @GetMapping("/{id}")
    public ResponseResult selectLink(@PathVariable("id") Long id){
        Link link = linkService.getById(id);
        return ResponseResult.okResult(link);
    }

    /**
     * 修改友链
     */
    @PutMapping
    public ResponseResult alterLink(@RequestBody Link link){
        linkService.updateById(link);
        return ResponseResult.okResult();
    }

    /**
     * 删除友链
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable("id") Long id){
        linkService.removeById(id);
        return ResponseResult.okResult();
    }
}
