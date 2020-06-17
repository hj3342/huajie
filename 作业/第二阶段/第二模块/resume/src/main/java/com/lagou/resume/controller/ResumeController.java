package com.lagou.resume.controller;

import com.lagou.resume.pojo.Resume;
import com.lagou.resume.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @RequestMapping("/queryAll")
    public ModelAndView queryAll() throws Exception {
        ModelAndView mv = new ModelAndView("resume");
        mv.addObject("resumeList",this.resumeService.queryAccountList());
        return mv;
    }


    @RequestMapping("/create")
    @ResponseBody
    public String create(Resume resume) throws Exception {
        try {
            this.resumeService.insert(resume);
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return "sucess";
    }

    @RequestMapping("/toUpdate")
    @ResponseBody
    public Resume toUpdate(Long id, ModelMap map) throws Exception {
        return this.resumeService.findById(id);
    }


    @RequestMapping("/update")
    @ResponseBody
    public String update(Resume resume) throws Exception {
        try {
            this.resumeService.update(resume);
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return "sucess";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(Long id) throws Exception {
        try {
            this.resumeService.delele(id);
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return "success";
    }

}
