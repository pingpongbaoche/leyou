package com.leyou.order.mapper;

import com.leyou.order.pojo.Address;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


@Mapper
//@Component
public interface AddressMapper extends tk.mybatis.mapper.common.Mapper<Address> {
}
