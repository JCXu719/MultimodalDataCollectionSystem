/**
 * @FileName VideoService
 * @Description TODO
 * @Author 57004
 * @date 2024-08-15
 **/
package com.rgfxyjz.test.service;

import com.rgfxyjz.test.pojo.VideoFile;
import com.rgfxyjz.test.pojo.VideoFile;

import java.util.List;
import java.util.Map;

public interface VideoService {
    List<VideoFile> list();
    void save(VideoFile videoFile,String path);
    void update(VideoFile videoFile);
    VideoFile get(Integer id);
    boolean delete(Integer id);
    List<VideoFile> searchByVideoFilename(String videoFilename);
    VideoFile searchByVideoFileId(Integer Id);
    boolean isUserCollected(String username);
    Map<String,Object> getVideoInfo(String url);
}
