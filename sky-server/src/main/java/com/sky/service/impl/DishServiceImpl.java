package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper flavorMapper;
    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        //插入菜品
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dish.setStatus(StatusConstant.ENABLE);
        dishMapper.insert(dish);
        Long dishId=dish.getId();
        //批量插入口味信息
        List<DishFlavor>list=dishDTO.getFlavors();
        if(list!=null && list.size()!=0){
            list.forEach(flavor -> {
                flavor.setDishId(dishId);
            });
            flavorMapper.insertBatch(list);
        }
    }

    @Override
    public PageResult pagequery(DishPageQueryDTO dto) {
        PageHelper.startPage(dto.getPage(),dto.getPageSize());
        Page<DishVO>result=dishMapper.pagequery(dto);
        long total=result.getTotal();
        List<DishVO>records=result.getResult();
        return new PageResult(total,records);
    }

    @Override
    public DishDTO getById(Integer id) {
        DishDTO dishDTO=new DishDTO();
        Dish dish=dishMapper.getById(id);
        List<DishFlavor>list=flavorMapper.getByDishId(id);
        BeanUtils.copyProperties(dish,dishDTO);
        dishDTO.setFlavors(list);
        return dishDTO;
    }

    @Override
    @Transactional
    public void editDish(DishDTO dto) {
        //修改单个菜品的信息
        Dish dish=new Dish();
        BeanUtils.copyProperties(dto,dish);
        dishMapper.update(dish);
        //修改该菜品下的口味，因为新增口味没有对应的id，无法进行update，只能先删除后插入
        //先删除原有口味
        List<Long>l=new ArrayList<>();
        l.add(dto.getId());
        flavorMapper.delete(l);
        //再添加新口味
        List<DishFlavor>list=dto.getFlavors();
        if(list!=null && list.size()!=0){
            list.forEach(flavor -> {
                flavor.setDishId(dto.getId());
            });
            flavorMapper.insertBatch(list);
        }
    }

    @Override
    public List<Dish> getByCategoryId(Integer id) {
        return dishMapper.getByCategoryId(id);
    }

    @Override
    public void setStatus(Integer status,long id) {
        Dish dish=new Dish();
        dish.setId(id);
        dish.setStatus(status);
        dishMapper.update(dish);
    }

    @Override
    public void delele(List<Long>ids) {
        log.info("删除菜品:{}",ids);
        flavorMapper.delete(ids);
        dishMapper.deleteByIds(ids);
    }
}
