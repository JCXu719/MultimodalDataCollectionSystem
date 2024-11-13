/**
 * @FileName User
 * @Description User entity class with fields for user details
 * @Author 57004
 * @date 2024-09-1
 **/
package com.rgfxyjz.test.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;

    // 学号
    private String studentId;

    // 用户名
    private String username;

    // 密码
    private String password;

    // 学院班级
    private String classCode;

    // 角色
    private Integer role;

    // QQ
    private String qq;

    // 性别 (0 = 女, 1 = 男)
    private Integer gender;

    // 文化水平 (0 = 小学及以下, 1 = 初高中, 2 = 本科, 3 = 硕博研究生)
    private Integer educationLevel;

    // 出生年月
    private String birthDate;

    // 手机号
    private String phone;

    // 头像URL
    private String avatarUrl;

    // 姓名
    private String fullname;
    
    // 职业
    private String career;

    // 兴趣
    private String hobbies;
}
