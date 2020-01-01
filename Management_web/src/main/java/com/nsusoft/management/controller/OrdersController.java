package com.nsusoft.management.controller;

import com.github.pagehelper.PageInfo;
import com.nsusoft.management.domain.Orders;
import com.nsusoft.management.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService service;

    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(value = "id", required = true) String ordersId) {
        Orders orders = service.findById(ordersId);
        return new ModelAndView("orders-show", "orders", orders);
    }

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(value = "page",required = true, defaultValue = "1") Integer page,
                                @RequestParam(value = "size",required = true, defaultValue = "4") Integer size) {
        List<Orders> orders = service.findAll(page, size);
        PageInfo pageInfo = new PageInfo(orders);
        return new ModelAndView("orders-list", "pageInfo", pageInfo);
    }
}
