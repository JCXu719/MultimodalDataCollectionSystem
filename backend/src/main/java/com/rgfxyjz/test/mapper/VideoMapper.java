/**
 * @FileName VideoMapper
 * @Description TODO
 * @Author 57004
 * @date 2024-08-15
 **/
package com.rgfxyjz.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rgfxyjz.test.pojo.VideoFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VideoMapper extends BaseMapper<VideoFile> {

}
