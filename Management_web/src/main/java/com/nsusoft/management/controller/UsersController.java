package com.nsusoft.management.controller;

import com.github.pagehelper.PageInfo;
import com.nsusoft.management.domain.Role;
import com.nsusoft.management.domain.UserInfo;
import com.nsusoft.management.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UsersController {
    @Autowired
    private UsersService service;

    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(value = "id", required = true) String userId) {
        //根据用户ID查询用户
        UserInfo userInfo = service.findById(userId);
        //根据用户ID查询可以添加的角色
        List<Role> roles = service.findOtherRoles(userId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", userInfo);
        modelAndView.addObject("roleList", roles);
        modelAndView.setViewName("user-role-add");
        return modelAndView;
    }

    @RequestMapping("/addRoleToUser.do")
    public String addRoleToUser(@RequestParam(value = "userId", required = true) String userId,
                                @RequestParam(value = "ids", required = true) String[] roleIds) {
        service.addRoleToUser(userId, roleIds);
        return "redirect:/user/findAll.do";
    }

    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(value = "id", required = true) String userId) {
        UserInfo userInfo = service.findById(userId);
        return new ModelAndView("user-show", "user", userInfo);
    }

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(value = "page",required = true, defaultValue = "1") Integer page,
                                @RequestParam(value = "size",required = true, defaultValue = "4") Integer size) {
        List<UserInfo> userInfos = service.findAll(page, size);
        PageInfo pageInfo = new PageInfo(userInfos);
        return new ModelAndView("user-list", "pageInfo", pageInfo);
    }

    @RequestMapping("/save.do")
    public String save(UserInfo userInfo) {
        service.save(userInfo);
        return "redirect:/user/findAll.do";
    }
}
