package com.dawson.Controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.dawson.domain.DTO.CategoryDto;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.Category;
import com.dawson.domain.vo.ExcelCategoryVo;
import com.dawson.enums.AppHttpCodeEnum;
import com.dawson.service.CategoryService;
import com.dawson.utils.BeanCopyUtils;
import com.dawson.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/content/category")
@Api(tags = "文章分类")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @ApiOperation(value = "获取所有的分类")
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }


    //用来生成分类数据的excle文件
    @PreAuthorize("@ps.hasPermission('content:category:export')")//授权检查
    @GetMapping("/export")
    @ApiOperation(value = "导出分类的excel文件")
    public void export(HttpServletResponse response){

        try {
            //设置响应方的头部、编码等
            WebUtils.setDownLoadHeader("分类.xlsx", response);

            //查询分类的数据
            List<Category> categories = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyListBean(categories, ExcelCategoryVo.class);

            //设置写流
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("导出数据")
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {
//            e.printStackTrace();
            //如果出现异常也要响应json。为什么要这样做呢？因为这个方法没有
            // 确定返回的格式，成功就下载文件，失败就输出字符串。所以就用renderString方法来返回
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

    //分页查询，相似查询
    @GetMapping("/list")
    @ApiOperation(value = "分类查询")
    public ResponseResult pageList(Long pageNum, Long pageSize, String name, String status){
        return categoryService.pageListLike(pageNum, pageSize, name, status);
    }

    /**
     * 增加分类
     */
    @PostMapping
    @ApiOperation(value = "添加分类")
    public ResponseResult addCategory(@RequestBody Category category){
        categoryService.save(category);
        return ResponseResult.okResult();
    }

    /**
     * 查询分类
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取分类信息")
    public ResponseResult getCategory(@PathVariable("id") Long id){
        Category category = categoryService.getById(id);
        return ResponseResult.okResult(category);
    }

    /**
     * 更新分类
     */
    @PutMapping
    @ApiOperation(value = "修改分类")
    public ResponseResult alterCategory(@RequestBody Category category){
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除分类")
    public ResponseResult deleteCategory(@PathVariable("id") Long id){
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }







}
