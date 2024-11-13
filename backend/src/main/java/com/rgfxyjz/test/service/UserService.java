package com.rgfxyjz.test.service;

import com.rgfxyjz.test.pojo.User;

import java.util.List;

public interface UserService {
    List<User> list();
    void save(User user);
    void update(User user);
    User get(Integer id);
    void delete(Integer id);
    List<User> searchByUsername(String username);

    List<User> searchByStudentId(String studentId);
    User login(String userinfo, String password);  // 新增的方法

    boolean existsByUsername(String username);
    boolean existsByStudentId(String studentId);


}
