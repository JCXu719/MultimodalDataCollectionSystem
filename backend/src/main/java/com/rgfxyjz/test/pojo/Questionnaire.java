/**
 * @FileName Questionnaire
 * @Description TODO
 * @Author 57004
 * @date 2024-08-24
 **/
package com.rgfxyjz.test.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@TableName("questionnaire")
@Data
public class Questionnaire {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private String description;

    private String content;


}
