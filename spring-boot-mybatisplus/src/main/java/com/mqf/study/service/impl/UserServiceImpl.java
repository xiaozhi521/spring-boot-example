package com.mqf.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mqf.study.beans.User;
import com.mqf.study.mapper.UserMapper;
import com.mqf.study.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mqf
 * @since 2019-02-13
 */
@Service
@CacheConfig(cacheNames="user") //抽取公共配置
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    //不缓存 id 为 1 记录
//    @Cacheable(value = "user",keyGenerator = "getKeyGenerator",condition = "#id==1",unless = "#id==2")
//    @Cacheable(cacheNames = "user",key="#id")
    @Cacheable(/*value = "user", */ key="#id")
    public User getUserById(Long id){
        User user = new User();
        user = user.selectById(id);
        return user;
    }

    /**
     * @CachePut : 既调用方法，有更新缓存数据
     * @return
     */
    @Override
    @CachePut(/*value = "user", */key = "#result.id")
    public User updateUser(User user) {
        baseMapper.updateById(user);
        return user;
    }

    @Override
    @CachePut(/*value = "user", */ key = "#result.id")
    public User insertUser(User user) {
        baseMapper.insert(user);
        return user;
    }

    @Override
    @CacheEvict(/*value = "user", */key = "#id")
    public int deleteUser(Long id) {
        int delete = baseMapper.deleteById(id);
        return delete;
    }

    @Override
    //定义复杂的缓存规则
    @Caching(
        cacheable = {
            @Cacheable(/*value = "user", */ key = "#name")
        },
        put = {
            @CachePut(/*value = "user", */ key = "#result.id"),
            @CachePut(/*value = "user", */ key = "#result.email")
        }
    )
    public User getUserByName(String name) {
        List<User> userList = baseMapper.selectList(new QueryWrapper<User>().like("name", name));
        return userList.get(0);
    }

}
