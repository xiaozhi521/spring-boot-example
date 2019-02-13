package com.mqf.study.service.impl;

import com.mqf.study.beans.User;
import com.mqf.study.mapper.UserMapper;
import com.mqf.study.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mqf
 * @since 2019-02-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
