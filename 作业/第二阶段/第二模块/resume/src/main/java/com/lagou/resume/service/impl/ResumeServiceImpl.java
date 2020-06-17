package com.lagou.resume.service.impl;

import com.lagou.resume.mapper.ResumeDao;
import com.lagou.resume.pojo.Resume;
import com.lagou.resume.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeDao resumeDao;

    @Override
    public List<Resume> queryAccountList() throws Exception {
        return resumeDao.findAll();
    }

    @Override
    public void insert(Resume resume) throws Exception {
        this.resumeDao.save(resume);
    }


    @Override
    public void update(Resume resume) throws Exception {
        this.resumeDao.save(resume);
    }

    @Override
    public void delele(Long id) throws Exception {
        this.resumeDao.deleteById(id);
    }

    @Override
    public Resume findById(Long id) throws Exception {
        return this.resumeDao.findById(id.longValue()).get();
    }
}
