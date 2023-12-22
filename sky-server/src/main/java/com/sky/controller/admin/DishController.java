package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api("菜品管理接口")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;
    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO){
        //删除redis中该菜品所属种类的缓存
        cleanCache("dish_"+dishDTO.getCategoryId());
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
    public Result<DishDTO>getById(@PathVariable Long id){
        DishDTO dishDTO=dishService.getById(id);
        return Result.success(dishDTO);
    }
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> getByCategoryId(Long categoryId){
        List<Dish>list=dishService.getByCategoryId(categoryId);
        return Result.success(list);
    }
    @PutMapping
    @ApiOperation("修改菜品")
    public Result editDish(@RequestBody DishDTO dto) {
        Long old_categoryId=dishService.getById(dto.getId()).getCategoryId();
        Long new_categoryId=dto.getCategoryId();
        //如果修改前后菜品分类不同，则要删除两个分类的缓存
        if(old_categoryId!=new_categoryId){
            cleanCache("dish_"+old_categoryId);
        }
        //删除缓存
        cleanCache("dish_"+new_categoryId);
        dishService.editDish(dto);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售、停售")
    public Result setStatus(@PathVariable Integer status,long id)
    {
        DishDTO dishDTO=dishService.getById(id);
        //删除缓存
        log.info("清除缓存"+"dish_"+dishDTO.getCategoryId());
        cleanCache("dish_"+dishDTO.getCategoryId());
        dishService.setStatus(status,id);

        return Result.success();
    }
    @DeleteMapping
    @ApiOperation("批量删除菜品")
    public Result delete(@RequestParam List<Long>ids)
    {
        Set<Long> category_ids=new HashSet<>();
        for(Long id:ids)
        {
            category_ids.add(dishService.getById(id).getCategoryId());
        }
        for(Long id:category_ids)
        {
            cleanCache("dish_"+id);
        }
        dishService.delele(ids);
        return Result.success();
    }
    private void cleanCache(String key)
    {
        redisTemplate.delete(key);
    }
}
