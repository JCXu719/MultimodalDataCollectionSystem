package com.rgfxyjz.test.service;

import com.rgfxyjz.test.pojo.Picture;
import com.rgfxyjz.test.pojo.VideoFile;

import java.util.List;

public interface PictureService {
    List<Picture> list();
    void save(Picture picture,String path);
    void update(Picture picture);
    Picture get(Integer id);
    boolean delete(Integer id);
    List<Picture> searchByPictureName(String pictureName);
    Picture searchByPictureFileId(Integer Id);
}