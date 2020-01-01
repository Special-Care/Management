package com.nsusoft.management.service;

import com.github.pagehelper.PageHelper;
import com.nsusoft.management.domain.Orders;
import com.nsusoft.management.mapper.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrdersService {
    @Autowired
    private OrdersMapper mapper;

    public List<Orders> findAll(int page, int size) {
        PageHelper.startPage(page, size);
        return mapper.queryAllOrders();
    }

    public Orders findById(String ordersId) {
        return mapper.queryOrdersById(ordersId);
    }
}
