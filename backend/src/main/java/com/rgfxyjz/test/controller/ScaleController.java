package com.rgfxyjz.test.controller;

import com.rgfxyjz.test.cjo.Result;
import com.rgfxyjz.test.pojo.Scale;
import com.rgfxyjz.test.pojo.User;
import com.rgfxyjz.test.service.ScaleService;
import com.rgfxyjz.test.service.ScoreService;
import com.rgfxyjz.test.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scale")
public class ScaleController {

    @Autowired
    private ScaleService scaleService;
    @Autowired
    private ScoreService scoreService;

    @Value("${app.users.data}")
    String userDataPath;

    // 创建量表

    @PostMapping("/upload")
    public Result create(@RequestBody Scale scale, @RequestHeader("token") String token) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) { // 管理员权限
                scaleService.save(scale);
                return Result.success("量表创建成功");
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("创建量表失败: " + e.getMessage());
        }
    }

    // 更新量表
    @PutMapping("/{id}")
    public Result update(@PathVariable Long id,@RequestBody Scale scale, @RequestHeader("token") String token) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) { // 管理员权限
                scale.setId(id);
                scaleService.update(scale);
                return Result.success("量表更新成功");
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("更新量表失败: " + e.getMessage());
        }
    }

    // 获取量表列表
    @GetMapping
    public Result list(@RequestHeader("token") String token) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) { // 管理员权限
                return Result.success(scaleService.list());
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("获取量表列表失败: " + e.getMessage());
        }
    }

    // 获取量表
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id, @RequestHeader("token") String token) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) { // 管理员权限
                Scale scale = scaleService.get(id);
                if (scale != null) {
                    return Result.success(scale);
                } else {
                    return Result.error("量表不存在");
                }
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("获取量表详情失败: " + e.getMessage());
        }
    }

    // 删除量表
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id, @RequestHeader("token") String token) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) { // 管理员权限
                scoreService.deleteByScaleId(id); //关联删除得分信息
                scaleService.delete(id);
                return Result.success("量表删除成功");
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("删除量表失败: " + e.getMessage());
        }
    }



}
