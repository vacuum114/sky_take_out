package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetmealServiceImlp implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Override
    public PageResult pagequery(SetmealPageQueryDTO pageQueryDTO) {
        PageHelper.startPage(pageQueryDTO.getPage(),pageQueryDTO.getPageSize());
        Page<SetmealVO>result=setmealMapper.pagequery(pageQueryDTO);
        long total=result.getTotal();
        List<SetmealVO> list=result.getResult();
        return  new PageResult(total,list);
    }
    @Override
    @Transactional
    public void save(SetmealDTO setmealDTO) {
        //添加套餐
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.insert(setmeal);
        //添加套餐中的菜品
        long id=setmeal.getId();
        List<SetmealDish>list=setmealDTO.getSetmealDishes();
        list.forEach(setmealDish -> {
            setmealDish.setSetmealId(id);
        });
        setmealDishMapper.insertBatch(list);

    }

    @Override
    @Transactional
    public void editSetmeal(SetmealDTO setmealDTO) {
        //修改套餐
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.update(setmeal);
        long setmealId=setmeal.getId();
        //先删除原有套餐内菜品
        List<Long>l=new ArrayList<>();
        l.add(setmealId);
        setmealDishMapper.delete(l);
        //再添加修改后的菜品
        List<SetmealDish>list=setmealDTO.getSetmealDishes();
        list.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
        });
        setmealDishMapper.insertBatch(list);

    }

    @Override
    public SetmealVO getById(long id) {
        Setmeal setmeal=setmealMapper.getById(id);
        SetmealVO setmealVO=new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVO);
        List<SetmealDish>list=setmealDishMapper.getBySealId(id);
        setmealVO.setSetmealDishes(list);
        return setmealVO;
    }

    @Override
    public void setStatus(Integer status, long id) {
        Setmeal setmeal=new Setmeal();
        setmeal.setId(id);
        setmeal.setStatus(status);
        setmealMapper.update(setmeal);
    }

    @Override
    public void delete(List<Long> ids) {
        setmealMapper.delete(ids);
        setmealDishMapper.delete(ids);
    }
}
