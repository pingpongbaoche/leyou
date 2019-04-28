package com.leyou.page.client;

import com.leyou.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 远程调用查询商品规格
 * */
@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {
}
