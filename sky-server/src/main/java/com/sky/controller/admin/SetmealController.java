package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api("套餐管理")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @GetMapping("/page")
    @ApiOperation("套餐分页查询")
    public Result<PageResult>pagequery(SetmealPageQueryDTO pageQueryDTO)
    {
        PageResult result=setmealService.pagequery(pageQueryDTO);
        return Result.success(result);
    }
    @PostMapping
    @ApiOperation("新增套餐")
    public Result save(@RequestBody SetmealDTO setmealDTO)
    {
        setmealService.save(setmealDTO);
        return Result.success();
    }
    @PutMapping
    @ApiOperation("修改套餐")
    public Result editSetmeal(@RequestBody SetmealDTO setmealDTO)
    {
        setmealService.editSetmeal(setmealDTO);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getById(@PathVariable long id)
    {
        SetmealVO setmealVO=setmealService.getById(id);
        return Result.success(setmealVO);
    }
    @PostMapping("/status/{status}")
    @ApiOperation("套餐起售、停售")
    public Result setStatus(@PathVariable Integer status,long id)
    {
        setmealService.setStatus(status,id);
        return Result.success();
    }
    @DeleteMapping
    @ApiOperation("批量删除套餐")
    public Result delete(@RequestParam List<Long> ids)
    {
        setmealService.delete(ids);
        return Result.success();
    }
}
