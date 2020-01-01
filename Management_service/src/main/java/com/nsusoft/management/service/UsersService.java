package com.nsusoft.management.service;

import com.github.pagehelper.PageHelper;
import com.nsusoft.management.domain.Role;
import com.nsusoft.management.domain.UserInfo;
import com.nsusoft.management.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsersService {
    @Autowired
    private UserMapper mapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserInfo findById(String userId) {
        return mapper.queryUserByUserId(userId);
    }

    public List<UserInfo> findAll(int page, int size) {
        PageHelper.startPage(page, size);
        return mapper.queryAllUsers();
    }

    public void save(UserInfo userInfo) {
        userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
        mapper.insertUser(userInfo);
    }

    public List<Role> findOtherRoles(String userId) {
        return mapper.queryOtherRoles(userId);
    }

    public void addRoleToUser(String userId, String[] roleIds) {
        for (String roleId:roleIds) {
            mapper.addRoleToUser(userId, roleId);
        }
    }
}
