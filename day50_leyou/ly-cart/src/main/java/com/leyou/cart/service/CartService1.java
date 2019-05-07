package com.leyou.cart.service;

import com.leyou.auth.entiy.UserInfo;
import com.leyou.cart.client.GoodsClient;
import com.leyou.cart.interceptor.LoginIntercepter;
import com.leyou.cart.pojo.Cart;
import com.leyou.common.utils.JsonUtils;
import com.leyou.item.pojo.Sku;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CartService1 {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private static final String KEY_PREFIX = "ly:cart:uid:";

    @Autowired
    private GoodsClient goodsClient;

    public void addCart(Cart cart) {
        //获取用户信息
        UserInfo userInfo = LoginIntercepter.getUserInfo();

        //查询购物车记录
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());

        String key = cart.getSkuId().toString();
        Integer num = cart.getNum();

        //判断当前的商品是否在购物车中
        if (hashOperations.hasKey(key)) {
            //在，更新数量
            String cartJson = hashOperations.get(key).toString();
            cart = JsonUtils.parse(cartJson, Cart.class);//反序列化
            cart.setNum(cart.getNum() + num);

        }else {
            //不在，新增购物车
            Sku sku = this.goodsClient.queryaSkuBySkuId(cart.getSkuId());
            cart.setUserId(userInfo.getId());
            cart.setTitle(sku.getTitle());
            cart.setOwnSpec(sku.getOwnSpec());
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(),",")[0]);
            cart.setPrice(sku.getPrice());
            hashOperations.put(key, cart);
        }

        hashOperations.put(key, JsonUtils.serialize(cart));

    }
}
