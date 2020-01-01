package com.nsusoft.management.service;

import com.nsusoft.management.domain.Role;
import com.nsusoft.management.domain.UserInfo;
import com.nsusoft.management.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserInfo userInfo = mapper.queryUserByUserName(username);
            if (userInfo == null)
                return null;

            UserDetails userDetails = new User(userInfo.getUsername(), userInfo.getPassword(),
                    userInfo.getStatus() == 1? true : false,true, true,
                    true,getAuthorities(userInfo.getRoles()));
            return userDetails;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<SimpleGrantedAuthority> getAuthorities(List<Role> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        for (Role role:roles)
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        return authorities;
    }
}
