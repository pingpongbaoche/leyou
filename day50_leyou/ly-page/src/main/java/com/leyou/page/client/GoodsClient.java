package com.leyou.page.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 远程调用商品查询
 * */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
    //这样做会造成代码的重复，我这边和服务提供方那边重复，但是我这边用的话一定要用接口声明，没什么好办法。
    //每一个调用微服务的人都要写接口声明的代码，重复太多啦
    //还有一个问题，就是我调用方怎么知道被调用方（服务提供方）精确的@GetMapping("/spu/detail/{id}")路径呢？就算我知道了，万一人家又改了呢？
    //由调用方写接口声明是不合适的，应该由服务提供方 ly-item-interface 提供GoodsApi，然后我们这继承
//    /**
//     * 根据spu的id查询商品详情（用于修改商品回显）
//     */
//    @GetMapping("/spu/detail/{id}")
//    SpuDetail queryDetailById(@PathVariable("id") Long spuId);
//
//
//    /**
//     * 根据spu查询所有sku
//     * */
//    @GetMapping("sku/list")
//    List<Sku> querySkuBySpuId(@RequestParam("id") Long spuId);
//
//    /**
//     * 分页查询spu
//     */
//    @GetMapping("spu/page")
//    PageResult<Spu> querySpuByPage(
//            @RequestParam(value = "page", defaultValue = "1") Integer page,
//            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
//            @RequestParam(value = "saleable", required = false) Boolean saleable,
//            @RequestParam(value = "key", required = false) String key);

}
