package com.nsusoft.management.mapper;

import com.nsusoft.management.domain.Traveller;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TravellerMapper {
    @Select("select * from traveller where id in (select travellerId from order_traveller where orderId=#{ordersId})")
    List<Traveller> queryTravellersByOrdersId(String ordersId);
}
