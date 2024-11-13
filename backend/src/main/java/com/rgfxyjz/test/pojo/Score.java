package com.rgfxyjz.test.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@TableName("score")
@Data
public class Score {

    @TableId(type = IdType.AUTO)
    private Long id;                  // 主键ID，自动递增

    private Long scaleId;     // 问卷或量表ID

    private String scaleUrl;    // 量表地址
    private String username;    // 用户名
    @JsonFormat(locale="zh",timezone="GMT+8", pattern="yyyy-MM-dd")
    private Timestamp uploadTime;
    private String scores;            // 分数，以逗号分隔的字符串

}
