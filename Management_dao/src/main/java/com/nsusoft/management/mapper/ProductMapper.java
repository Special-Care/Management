package com.nsusoft.management.mapper;

import com.nsusoft.management.domain.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductMapper {
    @Select("select * from product")
    List<Product> queryAllProduct();

    @Select("select * from product where id = #{productId}")
    List<Product> queryProductByProductId(String productId);

    @Insert("insert into product values(replace(uuid(),'-',''),#{productNum},#{productName},#{cityName},#{departureTime},#{productPrice},#{productDesc},#{productStatus})")
    void insertProduct(Product product);
}
