package com.nsusoft.management.controller;

import com.github.pagehelper.PageInfo;
import com.nsusoft.management.domain.SysLog;
import com.nsusoft.management.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/sysLog")
public class SysLogController {
    @Autowired
    private SysLogService service;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(value = "page",required = true, defaultValue = "1") Integer page,
                                @RequestParam(value = "size",required = true, defaultValue = "4") Integer size) {
        List<SysLog> sysLogs = service.findAll(page, size);
        PageInfo pageInfo = new PageInfo(sysLogs);
        return new ModelAndView("syslog-list", "pageInfo", pageInfo);
    }
}
