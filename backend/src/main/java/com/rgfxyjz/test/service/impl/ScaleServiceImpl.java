package com.rgfxyjz.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rgfxyjz.test.mapper.ScaleMapper;
import com.rgfxyjz.test.pojo.Scale;
import com.rgfxyjz.test.service.ScaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScaleServiceImpl implements ScaleService {

    @Autowired
    private ScaleMapper scaleMapper;

    @Override
    public List<Scale> list() {
        return scaleMapper.selectList(null);
    }

    @Override
    public void save(Scale scale) {
        if (scale.getId() == null) {
            scaleMapper.insert(scale);
        } else {
            scaleMapper.updateById(scale);
        }
    }

    @Override
    public void update(Scale scale) {
        scaleMapper.updateById(scale);
    }

    @Override
    public Scale get(Long id) {
        return scaleMapper.selectById(id);
    }

    @Override
    public void delete(Long id) {
        scaleMapper.deleteById(id);
    }



    @Override
    public Scale getByName(String name){
        return scaleMapper.selectOne(new QueryWrapper<Scale>().eq("name", name));
    }


}
