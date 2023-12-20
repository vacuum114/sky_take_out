package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类相关接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult>pagequery(CategoryPageQueryDTO queryDTO)
    {
        log.info("分页查询分类:{}",queryDTO);
        PageResult result=categoryService.pagequry(queryDTO);
        return Result.success(result);
    }
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> getByType(Integer type)
    {
        log.info("根据类型查询分类：{}",type);
        List<Category> result=categoryService.getByType(type);
        return Result.success(result);
    }
    @PostMapping
    @ApiOperation("新增分类")
    public Result save(@RequestBody CategoryDTO categoryDTO)
    {
        log.info("新增分类:{}",categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }
    @PutMapping
    @ApiOperation("修改分类")
    public Result editcategory(@RequestBody CategoryDTO categoryDTO)
    {
        log.info("修改分类:{}",categoryDTO);
        categoryService.editcategory(categoryDTO);
        return Result.success();
    }
    @PostMapping("/status/{status}")
    @ApiOperation("启用、禁用分类")
    public Result setStatus(@PathVariable Integer status,Long id)
    {
        log.info("修改分类状态:{}",status);
        categoryService.setStatus(status,id);
        return Result.success();
    }
    @DeleteMapping
    @ApiOperation("根据id删除分类")
    public Result deleteById(Long id)
    {
        categoryService.deleteById(id);
        return Result.success();
    }
}
