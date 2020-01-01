package com.nsusoft.management.controller;

import com.github.pagehelper.PageInfo;
import com.nsusoft.management.domain.Permission;
import com.nsusoft.management.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService service;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(value = "page",required = true, defaultValue = "1") Integer page,
                                @RequestParam(value = "size",required = true, defaultValue = "4") Integer size) {
        List<Permission> permissions = service.findAll(page, size);
        PageInfo pageInfo = new PageInfo(permissions);
        return new ModelAndView("permission-list", "pageInfo", pageInfo);
    }

    @RequestMapping("/save.do")
    public String save(Permission permission) {
        service.save(permission);
        return "redirect:/permission/findAll.do";
    }
}
