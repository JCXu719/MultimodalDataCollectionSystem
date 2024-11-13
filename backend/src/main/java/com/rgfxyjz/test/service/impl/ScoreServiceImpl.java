package com.rgfxyjz.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rgfxyjz.test.mapper.ScoreMapper;
import com.rgfxyjz.test.pojo.Score;
import com.rgfxyjz.test.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    @Override
    public List<Score> list() {
        return scoreMapper.selectList(null);
    }

    @Override
    public void save(Score score, String path) {
        if (score.getId() == null) {
            scoreMapper.insert(score);
        } else {
            scoreMapper.updateById(score);
        }
    }

    @Override
    public void update(Score score) {
        scoreMapper.updateById(score);
    }

    @Override
    public Score get(Long id) {
        return scoreMapper.selectById(id);
    }

    @Override
    public void delete(Long id) {
        scoreMapper.deleteById(id);
    }
    @Override
    public void deleteByScaleId(Long id){
        scoreMapper.delete(new QueryWrapper<Score>().eq("scale_id", id));
    }

    @Override
    public List<Score> getByScaleId(Long id){
        return scoreMapper.selectList(new QueryWrapper<Score>().eq("scale_id", id));
    }

}
