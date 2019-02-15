package com.mqf.study.service;

import com.mqf.study.beans.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mqf
 * @since 2019-02-13
 */
public interface UserService extends IService<User> {
    public User getUserById(Long id);

    public User updateUser(User user);

    public User insertUser(User user);

    public int deleteUser(Long id);

    public User getUserByName(String name);
}
