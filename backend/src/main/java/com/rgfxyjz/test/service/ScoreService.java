package com.rgfxyjz.test.service;

import com.rgfxyjz.test.pojo.Score;
import java.util.List;

public interface ScoreService {

    // 获取所有得分记录
    List<Score> list();

    // 保存得分记录
    void save(Score score, String path);

    // 更新得分记录
    void update(Score score);

    // 根据ID获取得分记录
    Score get(Long id);

    // 根据ID删除得分记录
    void delete(Long id);

    //根据问卷id删除得分记录
    void deleteByScaleId(Long id);

    //根据问卷id获取得分记录
    List<Score> getByScaleId(Long id);
}
