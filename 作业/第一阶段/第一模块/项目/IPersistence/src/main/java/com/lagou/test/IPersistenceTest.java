package com.lagou.test;

import com.lagou.dao.IUserDao;
import com.lagou.io.Resources;
import com.lagou.pojo.User;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionFactory;
import com.lagou.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class IPersistenceTest {
    private IUserDao userDao = null;
    //private SqlSession sqlSession = null;

    @Test
    public void test() throws Exception {
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //调用
        User user = new User();
        user.setId(2);
        user.setUsername("张三");
      /*  User user2 = sqlSession.selectOne("user.selectOne", user);

        System.out.println(user2);*/

       /* List<User> users = sqlSession.selectList("user.selectList");
        for (User user1 : users) {
            System.out.println(user1);
        }*/

        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        List<User> all = userDao.findAll();
        for (User user1 : all) {
            System.out.println(user1);
        }


    }

    @Before
    public void getSqlSession() throws Exception {
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        this.userDao = sqlSessionFactory.openSession().getMapper(IUserDao.class);

    }

    /**
     * 测试添加用户
     * @throws Exception
     */
    @Test
    public void testInsert() throws Exception {
        //创建新用户张三
        User user = new User();
        user.setId(2);
        user.setUsername("张三");
        //插入
        this.userDao.addUser(user);

        //遍历打印所有用户
        List<User> all = this.userDao.findAll();
        for (User user1 : all) {
            System.out.println(user1);
        }

    }

    /**
     * 测试更新用户
     * @throws Exception
     */
    @Test
    public void testUpdate() throws Exception {
        //将id为2的用户名改为李四
        User user = new User();
        user.setId(2);
        user.setUsername("李四");
        //更新
        this.userDao.updateUser(user);

        //遍历打印所有用户
        List<User> all = this.userDao.findAll();
        for (User user1 : all) {
            System.out.println(user1);
        }

    }

    /**
     * 测试删除用户
     * @throws Exception
     */
    @Test
    public void testDelete() throws Exception {
        User user = new User();
        user.setId(2);
        //删除用户id为2的用户
        this.userDao.deleteUser(user);

        //遍历打印所有用户
        List<User> all = this.userDao.findAll();
        for (User user1 : all) {
            System.out.println(user1);
        }

    }



}
