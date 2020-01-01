package com.nsusoft.management.service;

import com.github.pagehelper.PageHelper;
import com.nsusoft.management.domain.Product;
import com.nsusoft.management.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductMapper mapper;

    public List<Product> findAll(int page, int size) {
        PageHelper.startPage(page, size);
        return mapper.queryAllProduct();
    }

    public void save(Product product) {
        mapper.insertProduct(product);
    }

}
