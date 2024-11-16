/**
 * @FileName UserController
 * @Description TODO
 * @Author 57004
 * @date 2024-08-22
 **/
package com.rgfxyjz.test.controller;

import com.mzt.logapi.starter.annotation.LogRecord;
import com.rgfxyjz.test.cjo.Result;
import com.rgfxyjz.test.pojo.User;
import com.rgfxyjz.test.service.UserService;
import com.rgfxyjz.test.service.VideoService;
import com.rgfxyjz.test.service.impl.VideoServiceImpl;
import com.rgfxyjz.test.utils.FileUtils;
import com.rgfxyjz.test.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.apache.ibatis.logging.LogException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = Logger.getLogger(LogException.class.getName());
    @Autowired
    private UserService userService;
    @Autowired
    private VideoService videoService;

    @Value("${app.users.avatar-path}")
    String avatarBasePath;
    @Autowired
    private VideoServiceImpl videoServiceImpl;

    //以下为方法

    //注册
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        System.out.println(user);

        try {
            // 检查用户名是否已存在
            if (userService.existsByUsername(user.getUsername())) {
                return Result.error("用户名:" + user.getUsername() +"已存在");
            } else if (userService.existsByStudentId(user.getStudentId())) {
                return Result.error("学号:" + user.getStudentId() +"已存在");
            }
            // 保存用户信息到数据库
            userService.save(user);

            return Result.success("用户注册成功");

        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    //登录
    @PostMapping("/login")
    @LogRecord(
            success = "{{#user.username}}登录,登录结果:{{#_ret}}",
            type = "login", bizNo = "1")
    public Result login(@RequestBody User user,
                        @RequestParam Boolean RememberMe) {
        // 在这里获取的username，其实是userinfo（用户名/学号/手机号三选一）
        User user1 =  userService.login(user.getUsername(),user.getPassword());
        if(user1 !=null ){//自定义信息
            //使用JWT工具类，生成身份令牌
            String token = JwtUtils.genJwt(user1.getUsername(),user1.getId(),user1.getRole(),RememberMe,user1.getFullname(),user1.getStudentId());
            Result result = Result.success(token);
            result.setMsg(user1.getRole().toString());

            // 测试，获取token中的身份信息username
            return result;
        }
        return Result.error("用户名或密码错误");
    }

    //获取用户名单
    @GetMapping
    public Result list(@RequestHeader("token") String token) {
        try{
            if(JwtUtils.isTokenExpired(token)){
                throw new RuntimeException("Token expired");
            }
            if(JwtUtils.getRoleFromToken(token) == 0){
                return Result.success(userService.list());
            }
            else{
                return Result.error("无权限");
            }
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    //用户数量
    @GetMapping("/num")
    public Result num() {
        try{
            return Result.success(userService.list().size());
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }


    //保存
    @PostMapping("/save/{id}")
    public Result save(@RequestBody User user ,
                       @RequestHeader("token") String token,
                       @PathVariable Integer id) {

        try{
            if(JwtUtils.isTokenExpired(token)){
                throw new RuntimeException("Token expired");
            }
            if(JwtUtils.getRoleFromToken(token) == 0){
                if (userService.existsByUsername(user.getUsername())  ) {
                    return Result.error("用户名:" + user.getUsername() +"已存在");
                } else if (userService.existsByStudentId(user.getStudentId()) ) {
                    return Result.error("学号:" + user.getStudentId() +"已存在");
                }
                user.setId(id);
                userService.save(user);

                return Result.success("用户保存成功");
            }
            else{
                return Result.error("无权限");
            }
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    //更新
    @PutMapping("/update/{id}")
    public Result update(@RequestBody User user ,
                         @RequestHeader("token") String token,
                         @PathVariable Integer id) {
        try{
            if(JwtUtils.isTokenExpired(token)){
                throw new RuntimeException("Token expired");
            }
            if(JwtUtils.getRoleFromToken(token) == 0 || JwtUtils.getRoleFromToken(token) == 1 ){
                //禁止用户更新权限、id
                User user1 = userService.searchByStudentId(user.getStudentId()).get(0);

                if (userService.existsByUsername(user.getUsername()) ) {
                    return Result.error("用户名:" + user.getUsername() +"已存在");
                }
                user.setId(id);
                user.setRole(user1.getRole());

                userService.update(user);
                return Result.success("用户更新成功");
            }
            else{
                return Result.error("无权限");
            }
        }catch (Exception e){
            return Result.error(e.getMessage());
        }

    }


    //通过id获取用户
    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id,
                      @RequestHeader("token") String token) {
        try{
            if(JwtUtils.isTokenExpired(token)){
                throw new RuntimeException("Token expired");
            }
            if(JwtUtils.getRoleFromToken(token) == 0){
                User user = userService.get(id);
                if(user != null){
                    return Result.success(user);
                }else{
                    return  Result.error("未找到该用户");
                }

            }
            else{
                return Result.error("无权限");
            }
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }
    //通过令牌确定用户
    @GetMapping("/self")
    public Result getUserBytoken(@RequestHeader("token") String token) {
        if (JwtUtils.isTokenExpired(token)) {
            throw new RuntimeException("Token expired");
        }
        Integer userId = JwtUtils.getIdFromToken(token);

        return Result.success(userService.get(userId));
    }
    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id,
                         @RequestHeader("token") String token) {
        try{
            if(JwtUtils.isTokenExpired(token)){
                throw new RuntimeException("Token expired");
            }
            if(JwtUtils.getRoleFromToken(token) == 0){
                userService.delete(id);
                return Result.success("用户删除成功");
            }
            else{
                return Result.error("无权限");
            }
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    //查找
    @GetMapping("/search")
    public Result searchUserByUsername(@RequestParam String username,
                                       @RequestHeader("token") String token) {
        try{
            if(JwtUtils.isTokenExpired(token)){
                throw new RuntimeException("Token expired");
            }
            if(JwtUtils.getRoleFromToken(token) == 0 || JwtUtils.getRoleFromToken(token) == 1 ){
                return Result.success(userService.searchByUsername(username));
            }
            else{
                return Result.error("无权限");
            }
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }



    //更新头像
    @PostMapping("/uploadAvatar")
    public Result uploadAvatar(@RequestParam("avatar") MultipartFile image,
                               @RequestHeader("token") String token) {

        try {
            if(JwtUtils.isTokenExpired(token)){
                throw new RuntimeException("Token expired");
            }
            Integer userId = JwtUtils.getIdFromToken(token);
            String savePath = avatarBasePath + "/" + userId;
            FileUtils.saveFile(image,savePath,userId.toString());
            User user = userService.get(userId);
            user.setAvatarUrl(savePath);
            userService.save(user);
            return  Result.success();
        } catch (Exception e) {
            // 返回失败结果
            System.out.println(e.toString());
            return Result.error("上传头像失败" + e.getMessage());
        }
    }

    // 检验是否已经采集过
    @GetMapping("checkIfCollected")
    public Result checkIfCollected(@RequestHeader("token") String token) {
        System.out.println(token);
        // 根据token获取当前用户的用户名
        String username = JwtUtils.getUsernameFromToken(token);

        // 查找video_files表是否存在当前用户，需要临时使用VideoService
        System.out.println(videoService.isUserCollected(username));
        if (videoService.isUserCollected(username)){
            return Result.error("已采集过，拒绝采集");
        }else{
            return Result.success();
        }
    }


}



