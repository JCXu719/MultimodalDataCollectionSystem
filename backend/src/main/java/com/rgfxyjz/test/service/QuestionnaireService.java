package com.rgfxyjz.test.service;

import com.rgfxyjz.test.pojo.Questionnaire;

import java.util.List;

public interface QuestionnaireService {

    List<Questionnaire> list();

    void save(Questionnaire questionnaire);

    void update(Questionnaire questionnaire);

    Questionnaire get(Long id);

    void delete(Long id);

    List<String> parseContent(String content);
}
