package com.nsusoft.management.service;

import com.github.pagehelper.PageHelper;
import com.nsusoft.management.domain.SysLog;
import com.nsusoft.management.mapper.SysLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysLogService {
    @Autowired
    private SysLogMapper mapper;

    public void save(SysLog log) {
        mapper.insertSysLog(log);
    }

    public List<SysLog> findAll(int page, int size) {
        PageHelper.startPage(page, size);
        return mapper.queryAllLog();
    }
}
