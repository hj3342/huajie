package com.lagou.dao;

import com.lagou.pojo.User;

import java.util.List;

public interface IUserDao {

    //查询所有用户
    public List<User> findAll() throws Exception;

    //根据条件进行用户查询
    public User findByCondition(User user) throws Exception;

    /**
     * 添加用户
     * @param user
     * @throws Exception
     */
    public void addUser(User user) throws Exception;

    /**
     * 更新用户
     * @param user
     * @throws Exception
     */
    public void updateUser(User user) throws Exception;

    /**
     * 根据用户Id删除用户
     * @param user
     * @throws Exception
     */
    public void deleteUser(User user) throws Exception;


}
