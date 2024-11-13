/**
 * @FileName PictureServiceImpl
 * @Description TODO
 * @Author 57004
 * @date 2024-08-22
 **/
package com.rgfxyjz.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rgfxyjz.test.mapper.PictureMapper;
import com.rgfxyjz.test.pojo.Picture;
import com.rgfxyjz.test.pojo.Picture;
import com.rgfxyjz.test.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    PictureMapper pictureMapper;
    @Override
    public List<Picture> list(){
        return pictureMapper.selectList(null);
    };
    @Override
    public void save(Picture pictureFile,String path){
        if (pictureFile.getId() == null) {
            pictureMapper.insert(pictureFile);
        } else {
            pictureMapper.updateById(pictureFile);
        }
    };
    @Override
    public void update(Picture pictureFile){
        pictureMapper.updateById(pictureFile);
    };
    @Override
    public Picture get(Integer id){
        return pictureMapper.selectById(id);
    };
    @Override
    public boolean delete(Integer id){
        Picture picturFile = pictureMapper.selectById(id);
        if (picturFile != null) {
            // 获取文件的URL（路径）
            String url = picturFile.getUrl();
            // 删除文件系统中的文件
            File file = new File(url);
            if (file.exists()) {
                if (file.delete()) {
                    pictureMapper.deleteById(id);
                    return true;
                }
            }
        }
        return false;
    };

    @Override
    public List<Picture> searchByPictureName(String pictureFilename){
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", pictureFilename);
        return pictureMapper.selectList(queryWrapper);
    };

    @Override
    public Picture searchByPictureFileId(Integer Id){
        return pictureMapper.selectById(Id);
    };
}
