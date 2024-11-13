/**
 * @FileName VideoServiceImpl
 * @Description TODO
 * @Author 57004
 * @date 2024-08-15
 **/
package com.rgfxyjz.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rgfxyjz.test.mapper.VideoMapper;
import com.rgfxyjz.test.pojo.VideoFile;
import com.rgfxyjz.test.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    VideoMapper videoMapper;
    @Override
    public List<VideoFile> list(){
        return videoMapper.selectList(null);
    };
    @Override
    public void save(VideoFile videoFile,String path){
        if (videoFile.getId() == null) {
            videoMapper.insert(videoFile);
        } else {
            videoMapper.updateById(videoFile);
        }
    };

    @Override
    public void update(VideoFile videoFile){
        videoMapper.updateById(videoFile);
    };
    @Override
    public VideoFile get(Integer id){
        return videoMapper.selectById(id);
    };
    @Override
    public boolean delete(Integer id){
        VideoFile videoFile = videoMapper.selectById(id);
        if (videoFile != null) {
            // 获取文件的URL（路径）
            String url = videoFile.getUrl();
            // 删除文件系统中的文件
            File file = new File(url);
            if (file.exists()) {
                if (file.delete()) {
                    videoMapper.deleteById(id);
                    return true;
                }
            }
        }
        return false;
    };
    @Override
    public List<VideoFile> searchByVideoFilename(String videoFilename){
        QueryWrapper<VideoFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", videoFilename);
        return videoMapper.selectList(queryWrapper);

    };

    @Override
    public VideoFile searchByVideoFileId(Integer id){
        return  videoMapper.selectById(id);
    };

    @Override
    public boolean isUserCollected(String username){
        QueryWrapper<VideoFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        // 执行查询计数
        Long count = videoMapper.selectCount(queryWrapper);
        return count > 0;
    }
}
