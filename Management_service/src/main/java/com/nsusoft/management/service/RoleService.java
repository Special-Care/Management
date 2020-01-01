package com.nsusoft.management.service;

import com.github.pagehelper.PageHelper;
import com.nsusoft.management.domain.Permission;
import com.nsusoft.management.domain.Role;
import com.nsusoft.management.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleMapper mapper;

    public List<Role> findAll(int page, int size) {
        PageHelper.startPage(page, size);
        return mapper.queryAllRole();
    }

    public void save(Role role) {
        mapper.insertRole(role);
    }

    public Role findById(String roleId) {
        return mapper.queryRoleByRoleId(roleId);
    }

    public List<Permission> findOtherPermissions(String roleId) {
        return mapper.queryOtherPermissions(roleId);
    }

    public void addPermissionToRole(String roleId, String[] permissionIds) {
        for (String permissionId:permissionIds) {
            mapper.addPermissionToRole(roleId, permissionId);
        }
    }
}
