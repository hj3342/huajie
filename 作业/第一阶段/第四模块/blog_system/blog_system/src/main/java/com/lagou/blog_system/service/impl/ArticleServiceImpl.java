package com.lagou.blog_system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.blog_system.mapper.ArticleMapper;
import com.lagou.blog_system.pojo.Article;
import com.lagou.blog_system.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public PageInfo<Article> queryAll(Integer offset, Integer limit) {
        PageHelper.startPage(offset, limit);
        List<Article> articleList = this.articleMapper.queryAll();
        return new PageInfo<>(articleList);
    }
}
