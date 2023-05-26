package com.dawson.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dawson.constant.SystemConstants;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.Comment;
import com.dawson.domain.entity.User;
import com.dawson.domain.vo.CommentVo;
import com.dawson.domain.vo.PageVo;
import com.dawson.enums.AppHttpCodeEnum;
import com.dawson.exception.SystemException;
import com.dawson.mapper.CommentMapper;
import com.dawson.service.CommentService;
import com.dawson.service.UserService;
import com.dawson.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {


    @Autowired
    UserService userService;

    final Long rootId = -1L;//根评论的值
    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {



        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper();
        //查询指定文章的评论,如果是友联评论就不需要这一步
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId, articleId);
        //查询根评论
        queryWrapper.eq(Comment::getRootId, rootId);
        //评论的类型要匹配
        queryWrapper.eq(Comment::getType, commentType);


        //分页查询根评论
        Page<Comment> page = new Page(pageNum, pageSize);
        page(page, queryWrapper);

        List<Comment> articles = list(queryWrapper);
        List<CommentVo> commentVos = BeanCopyUtils.copyListBean(page.getRecords(), CommentVo.class);

        //因为数据库评论表里面没有username和children的值，所以要联系其它几个表去查一下
        //设置根评论的username
        List<CommentVo> commentVos_name = addUserName(commentVos);
        //设置子评论。下面这个无法执行，我也不知道为什么
//        commentVos_name.stream().map( f -> {
//            f.setChildren(getCommentChildren(f.getId()));
//            return f;
//        });

        //设置子评论
        commentVos_name.forEach( f -> {
            f.setChildren(getCommentChildren(f.getId()));
        });

        //设置子评论的名字
        List<CommentVo> commentVos_name_children = addUserName(commentVos_name);
        return ResponseResult.okResult(new PageVo(commentVos, page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {

        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }


    private List<CommentVo> getCommentChildren(Long commentId){

        //先查出来这条评论下面的子评论
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getRootId, commentId).orderByDesc(Comment::getCreateTime);
        List<Comment> comments = list(lambdaQueryWrapper);
        List<CommentVo> commentVoList = BeanCopyUtils.copyListBean(comments, CommentVo.class);

        //再给子评论一个个赋上名字
        commentVoList.forEach(f -> {
            User user = userService.getById(f.getCreateBy());
            System.out.println(user.getUserName());
            f.setUsername(user.getUserName());
            if(f.getToCommentId() != -1){
                f.setToCommentUserName(userService.getById(f.getToCommentUserId()).getNickName());
            }
        });
        return commentVoList;
    }


    private List<CommentVo> addUserName(List<CommentVo> commentVos){

        commentVos.forEach(f -> {
            User user = userService.getById(f.getCreateBy());
            f.setUsername(user.getNickName());
            //如果不是根评论，就根本没有ToCommentId这个字段
            if(f.getToCommentId() != -1){
                f.setToCommentUserName(userService.getById(f.getToCommentUserId()).getNickName());
            }
        });

        return commentVos;
    }
}


