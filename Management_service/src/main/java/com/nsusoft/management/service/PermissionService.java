package com.nsusoft.management.service;

import com.github.pagehelper.PageHelper;
import com.nsusoft.management.domain.Permission;
import com.nsusoft.management.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PermissionService {
    @Autowired
    private PermissionMapper mapper;

    public List<Permission> findAll(int page, int size) {
        PageHelper.startPage(page, size);
        return mapper.queryAllPermission();
    }

    public void save(Permission permission) {
        mapper.insertPermission(permission);
    }
}
