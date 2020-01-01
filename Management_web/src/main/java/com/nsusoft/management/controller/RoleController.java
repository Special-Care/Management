package com.nsusoft.management.controller;

import com.github.pagehelper.PageInfo;
import com.nsusoft.management.domain.Permission;
import com.nsusoft.management.domain.Role;
import com.nsusoft.management.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService service;

    @RequestMapping("/findRoleByIdAndAllPermission.do")
    public ModelAndView findRoleByIdAndAllPermission(@RequestParam(value = "id", required = true) String roleId) {
        //根据角色ID查询角色
        Role role = service.findById(roleId);
        //根据角色ID查询可以添加的权限
        List<Permission> permissions = service.findOtherPermissions(roleId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("role", role);
        modelAndView.addObject("permissionList", permissions);
        modelAndView.setViewName("role-permission-add");
        return modelAndView;
    }

    @RequestMapping("/addPermissionToRole.do")
    public String addPermissionToRole(@RequestParam(value = "roleId", required = true) String roleId,
                                      @RequestParam(value = "ids", required = true) String[] permissionIds) {
        service.addPermissionToRole(roleId, permissionIds);
        return "redirect:/role/findAll.do";
    }

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(value = "page",required = true, defaultValue = "1") Integer page,
                                @RequestParam(value = "size",required = true, defaultValue = "4") Integer size) {
        List<Role> roles = service.findAll(page, size);
        PageInfo pageInfo = new PageInfo(roles);
        return new ModelAndView("role-list", "pageInfo", pageInfo);
    }

    @RequestMapping("/save.do")
    public String save(Role role) {
        service.save(role);
        return "redirect:/role/findAll.do";
    }
}
