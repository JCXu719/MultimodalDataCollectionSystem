package com.rgfxyjz.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rgfxyjz.test.mapper.QuestionnaireMapper;
import com.rgfxyjz.test.pojo.Picture;
import com.rgfxyjz.test.pojo.Questionnaire;
import com.rgfxyjz.test.pojo.Scale;
import com.rgfxyjz.test.pojo.VideoFile;
import com.rgfxyjz.test.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    @Autowired
    private VideoService videoService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private ScaleService scaleService;

    @Override
    public List<Questionnaire> list() {
        return questionnaireMapper.selectList(null);
    }

    @Override
    public void save(Questionnaire questionnaire) {
        if (questionnaire.getId() == null) {
            questionnaireMapper.insert(questionnaire);
        } else {
            questionnaireMapper.updateById(questionnaire);
        }
    }

    @Override
    public void update(Questionnaire questionnaire) {
        questionnaireMapper.updateById(questionnaire);
    }

    @Override
    public Questionnaire get(Long id) {
        return questionnaireMapper.selectById(id);
    }

    @Override
    public void delete(Long id) {
        questionnaireMapper.deleteById(id);
    }

    @Override
    public List<String> parseContent(String content) {
        List<String> urls = new ArrayList<>();
        if (content == null || content.isEmpty()) {
            return urls;
        }

        String[] indices = content.split("_");
        for (String index : indices) {
            if (index.length() < 2) continue;
            char type = index.charAt(0);
            Integer itemId = Integer.parseInt(index.substring(1));

            if (type == 'p') {
                Picture picture = pictureService.get(itemId);
                if (picture != null) {
                    urls.add(picture.getUrl());
                }else{
                    return null;
                }
            } else if (type == 'v') {
                VideoFile video = videoService.get(itemId);
                if (video != null) {
                    urls.add(video.getUrl());
                }else{
                    return null;
                }
            } else if (type == 's') {
                continue;
            } else{
                return null;
            }
        }

        return urls;
    }
}
