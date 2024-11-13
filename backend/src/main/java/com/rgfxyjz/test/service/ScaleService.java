package com.rgfxyjz.test.service;

import com.rgfxyjz.test.pojo.Scale;

import java.util.List;

public interface ScaleService {
    List<Scale> list();

    void save(Scale scale);

    void update(Scale scale);

    Scale get(Long id);

    void delete(Long id);





    Scale getByName(String name);
}

