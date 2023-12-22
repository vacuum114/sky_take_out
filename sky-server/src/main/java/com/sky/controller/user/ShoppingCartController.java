package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api("购物车接口")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @PostMapping("/add")
    @ApiOperation("添加商品进购物车")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO)
    {
        shoppingCartService.ShoppingCartAdd(shoppingCartDTO);
        return Result.success();
    }
    @GetMapping("/list")
    @ApiOperation("查看购物车")
    public Result<List<ShoppingCart>> list(){
        List<ShoppingCart>shoppingCartList=shoppingCartService.list();
        return Result.success(shoppingCartList);
    }
    @PostMapping("/sub")
    @ApiOperation("删除购物车中的一件商品")
    public Result sub(@RequestBody ShoppingCartDTO shoppingCartDTO){
        shoppingCartService.sub(shoppingCartDTO);
        return Result.success();
    }
    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result clearAll(){
        shoppingCartService.clearAll();
        return Result.success();
    }
}
