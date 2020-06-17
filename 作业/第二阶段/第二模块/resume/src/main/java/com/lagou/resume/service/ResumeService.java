package com.lagou.resume.service;


import com.lagou.resume.pojo.Resume;

import java.util.List;

public interface ResumeService {
    /**
     * 查询所有简历信息
     * @return
     * @throws Exception
     */
    List<Resume> queryAccountList() throws Exception;

    /**
     * 添加简历人信息
     * @param resume
     * @throws Exception
     */
    void insert(Resume resume) throws Exception;

    /**
     * 修改简历信息
     * @param resume
     * @throws Exception
     */
    void update(Resume resume) throws Exception;

    /**
     * 删除简历信息
     * @param id
     * @throws Exception
     */
    void delele(Long id) throws Exception;

    /**
     * 根据Id查找简历
     * @param id
     * @throws Exception
     */
    Resume findById(Long id) throws Exception;
}
