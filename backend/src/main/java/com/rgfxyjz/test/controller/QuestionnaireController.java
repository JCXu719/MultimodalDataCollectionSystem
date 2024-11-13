package com.rgfxyjz.test.controller;

import com.rgfxyjz.test.cjo.Result;
import com.rgfxyjz.test.pojo.Questionnaire;
import com.rgfxyjz.test.service.QuestionnaireService;
import com.rgfxyjz.test.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;

    // 创建问卷
    @PostMapping
    public Result create(@RequestBody Questionnaire questionnaire, @RequestHeader("token") String token) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) { // 管理员权限
                if(questionnaireService.parseContent(questionnaire.getContent()) == null){
                    return Result.error("问卷格式错误或指定资源不存在");
                }
                questionnaireService.save(questionnaire);
                return Result.success("问卷创建成功");
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("创建问卷失败: " + e.getMessage());
        }
    }

    // 更新问卷
    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id,@RequestBody Questionnaire questionnaire, @RequestHeader("token") String token) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) { // 管理员权限
                questionnaire.setId(id.longValue());
                questionnaireService.update(questionnaire);
                return Result.success("问卷更新成功");
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("更新问卷失败: " + e.getMessage());
        }
    }

    // 获取问卷列表
    @GetMapping
    public Result list(@RequestHeader("token") String token) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) { // 管理员权限
                return Result.success(questionnaireService.list());
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("获取问卷列表失败: " + e.getMessage());
        }
    }

    // 获取问卷详情
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id, @RequestHeader("token") String token) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) { // 管理员权限
                Questionnaire questionnaire = questionnaireService.get(id);
                if (questionnaire != null) {
                    return Result.success(questionnaire);
                } else {
                    return Result.error("问卷不存在");
                }
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("获取问卷详情失败: " + e.getMessage());
        }
    }

    // 删除问卷
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id, @RequestHeader("token") String token) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) { // 管理员权限
                questionnaireService.delete(id);
                return Result.success("问卷删除成功");
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("删除问卷失败: " + e.getMessage());
        }
    }

    // 解析content字段
    @GetMapping("/content/{id}")
    public Result parseContent(@PathVariable Long id, @RequestHeader("token") String token) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) { // 管理员权限
                Questionnaire questionnaire = questionnaireService.get(id);
                if (questionnaire != null) {
                    List<String> urls = questionnaireService.parseContent(questionnaire.getContent());
                    return Result.success(urls);
                } else {
                    return Result.error("问卷不存在");
                }
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("解析内容失败: " + e.getMessage());
        }
    }
}
