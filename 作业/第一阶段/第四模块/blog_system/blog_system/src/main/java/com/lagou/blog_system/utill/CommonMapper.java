package com.lagou.blog_system.utill;



import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface CommonMapper<T> extends Mapper<T>, MySqlMapper<T> {
//这里实现一个自己的接口,继承通用的mapper，关键点就是这个接口不能被扫描到(不加注解就不会被扫描到)，
}
