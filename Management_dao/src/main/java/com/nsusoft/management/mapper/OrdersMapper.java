package com.nsusoft.management.mapper;

import com.nsusoft.management.domain.Orders;
import com.nsusoft.management.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrdersMapper {
    @Select("select * from orders")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "orderNum",column = "orderNum"),
            @Result(property = "orderTime",column = "orderTime"),
            @Result(property = "orderStatus",column = "orderStatus"),
            @Result(property = "peopleCount",column = "peopleCount"),
            @Result(property = "payType",column = "payType"),
            @Result(property = "orderDesc",column = "orderDesc"),
            @Result(property = "product",column = "productId",javaType = Product.class,
                    one = @One(select = "com.nsusoft.management.mapper.ProductMapper.queryProductByProductId"))
    })
    List<Orders> queryAllOrders();

    @Select("select * from orders where id=#{ordersId}")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "orderNum",column = "orderNum"),
            @Result(property = "orderTime",column = "orderTime"),
            @Result(property = "orderStatus",column = "orderStatus"),
            @Result(property = "peopleCount",column = "peopleCount"),
            @Result(property = "payType",column = "payType"),
            @Result(property = "orderDesc",column = "orderDesc"),
            @Result(property = "product",column = "productId",
                    one = @One(select = "com.nsusoft.management.mapper.ProductMapper.queryProductByProductId")),
            @Result(property="member",column="memberId",
                    one = @One(select="com.nsusoft.management.mapper.MemberMapper.queryMemberByMemberId")),
            @Result(property="travellers",column="id",javaType = List.class,
                    many = @Many(select="com.nsusoft.management.mapper.TravellerMapper.queryTravellersByOrdersId"))
    })
    Orders queryOrdersById(String ordersId);
}
