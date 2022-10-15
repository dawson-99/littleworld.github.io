package com.dawson.controller;


import com.dawson.annotation.SystemLog;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.Comment;
import com.dawson.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Api(tags = "评论")
public class CommentController {


    @Autowired
    CommentService commentService;


    @GetMapping("/commentList")
    @ApiOperation(value = "获取评论列表")
//    @SystemLog(businessName = "获取评论列表")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.commentList("0",articleId, pageNum, pageSize);
    }

    @GetMapping("/linkList")
    @ApiOperation(value = "获取友链列表")
//    @SystemLog(businessName = "获取友链列表")
    public ResponseResult LinkcommentList(Integer pageNum, Integer pageSize){
        return commentService.commentList("1",null, pageNum, pageSize);
    }


    @PostMapping
//    @SystemLog(businessName = "添加评论")
    @ApiOperation(value = "添加评论")
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }


}
