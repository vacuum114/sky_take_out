package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api("菜品管理接口")
public class DishController {
    @Autowired
    private DishService dishService;
    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品:{}",dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public  Result<PageResult>pagequery(DishPageQueryDTO dto) {
        log.info("菜品分页查询：{}",dto);
        PageResult result=dishService.pagequery(dto);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishDTO>getById(@PathVariable Integer id){
        DishDTO dishDTO=dishService.getById(id);
        return Result.success(dishDTO);
    }
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> getByCategoryId(Integer categoryId){
        List<Dish>list=dishService.getByCategoryId(categoryId);
        return Result.success(list);
    }

    @PutMapping
    @ApiOperation("修改菜品")
    public Result editDish(@RequestBody DishDTO dto) {
        dishService.editDish(dto);
        return Result.success();
    }
}
