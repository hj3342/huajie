package com.lagou.blog_system.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.blog_system.pojo.Article;
import com.lagou.blog_system.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/queryAll")
    public String queryAllArticle(ModelMap map, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "2") Integer pageSize
){
        PageInfo<Article> pageInfo = this.articleService.queryAll(pageNum,pageSize);
        map.addAttribute("page",pageInfo);
        map.addAttribute("articleList",pageInfo.getList());
        return "index";
    }
}
