package com.dawson.Controller;


import com.dawson.domain.DTO.AddArticleDto;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.vo.ArticleAlterVo;
import com.dawson.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {


    @Autowired
    ArticleService articleService;

    //添加文章
    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }

    //模糊查询文章
    @GetMapping("/list")
    public ResponseResult selectArticle(Long pageNum, Long pageSize, String title, String summary){
        return articleService.selectArticle(pageNum, pageSize, title, summary);
    }

    //用于返回到修改界面的接口
    @GetMapping("{id}")
    public ResponseResult getArticleById_To_Alter(@PathVariable("id") Long id){
        return articleService.getArticleById_To_Alter(id);
    }

    //更新文章
    @PutMapping
    public ResponseResult update(@RequestBody ArticleAlterVo articleAlterVo){
        return articleService.updateArticle(articleAlterVo);
    }

    //删除文章，逻辑删除
    @DeleteMapping("{id}")
    public ResponseResult deleteArticle(@PathVariable("id") Long id){
        return articleService.deleteArticle(id);
    }







}
