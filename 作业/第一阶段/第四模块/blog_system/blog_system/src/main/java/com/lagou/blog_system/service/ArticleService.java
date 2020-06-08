package com.lagou.blog_system.service;

import com.github.pagehelper.PageInfo;
import com.lagou.blog_system.pojo.Article;

public interface ArticleService {

    PageInfo<Article> queryAll(Integer offset, Integer limit);
}
