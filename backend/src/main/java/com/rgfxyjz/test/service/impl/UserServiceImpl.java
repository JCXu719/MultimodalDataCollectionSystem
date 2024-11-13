/**
 * @FileName UserServiceImpl
 * @Description TODO
 * @Author 57004
 * @date 2024-08-22
 **/
package com.rgfxyjz.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rgfxyjz.test.mapper.UserMapper;
import com.rgfxyjz.test.pojo.User;
import com.rgfxyjz.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> list() {
        return userMapper.selectList(null);
    }

    @Override
    public void save(User user) {
        if (user.getId() == null) {
            userMapper.insert(user);
        } else {
            userMapper.updateById(user);
        }
    }

    @Override
    public void update(User user) {
        userMapper.updateById(user);
    }

    @Override
    public User get(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public void delete(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public List<User> searchByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public User login(String userinfo, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        User return_user;

        // 首先查找username列
        queryWrapper.eq("username", userinfo).eq("password", password);
        return_user = userMapper.selectOne(queryWrapper);

        if(return_user == null) {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("student_id", userinfo).eq("password", password);
            return_user = userMapper.selectOne(queryWrapper);
            if(return_user == null) {
                queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("phone", userinfo).eq("password", password);
                return_user = userMapper.selectOne(queryWrapper);
                return return_user;
            }
            else{
                return return_user;
            }
        }
        else {
            return return_user;
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public boolean existsByStudentId(String studentId){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", studentId);
        return userMapper.selectCount(queryWrapper) > 0;
    }
    @Override
    public List<User> searchByStudentId(String studentId){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", studentId);
        return userMapper.selectList(queryWrapper);
    };

}
