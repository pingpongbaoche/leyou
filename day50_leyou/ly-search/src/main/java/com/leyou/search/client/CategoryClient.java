package com.leyou.search.client;

import com.leyou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 远程调用商品分类查询
 * */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {

//    @GetMapping("category/list/ids")
//    List<Category> queryCategoryByIds(@RequestParam("ids") List<Long> ids);
}
