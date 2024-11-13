/**
 * @FileName Scale
 * @Description TODO
 * @Author 57004
 * @date 2024-09-02
 **/
package com.rgfxyjz.test.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("scale")
@Data
public class Scale {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name; // 量表名称

    private String questions; // 以换行分隔的问题文本

    private String description; // 量表描述
}
