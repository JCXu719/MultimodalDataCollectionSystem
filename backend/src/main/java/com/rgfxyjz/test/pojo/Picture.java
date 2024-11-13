/**
 * @FileName Picture
 * @Description TODO
 * @Author 57004
 * @date 2024-08-22
 **/
package com.rgfxyjz.test.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@TableName("picture")
@Data
public class Picture {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String type;

    private Long size;

    private String url;
    @JsonFormat(locale="zh",timezone="GMT+8", pattern="yyyy-MM-dd")
    private Timestamp uploadTime;

    private String username;


}
