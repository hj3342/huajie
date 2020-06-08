package com.lagou.blog_system.mapper;

import com.lagou.blog_system.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleMapper {


    /**
     * 查询所有数据
     * @return
     */
    List<Article> queryAll();

}