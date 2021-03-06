package com.leyou.search.client;

import com.leyou.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 远程调用商品品牌查询
 * */
@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}
