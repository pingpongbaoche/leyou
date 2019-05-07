package com.leyou.cart.service;

import com.leyou.auth.entiy.UserInfo;
import com.leyou.cart.client.GoodsClient;
import com.leyou.cart.interceptor.LoginIntercepter;
import com.leyou.cart.pojo.Cart;
import com.leyou.common.utils.JsonUtils;
import com.leyou.item.pojo.Sku;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: taft
 * @Date: 2018-9-7 17:27
 */
@Service
public class CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private GoodsClient goodsClient;

    static final String KEY_PREFIX = "ly:cart:uid:";

    static final Logger logger = LoggerFactory.getLogger(CartService.class);

    public void addCart(Cart cart) {

        // 获取登录用户
        UserInfo user = LoginIntercepter.getUserInfo();


        System.out.println("添加购物查：" + cart);

        //首先查询服务端是否已经包含这个sku的购物车信息
        String key = KEY_PREFIX + user.getId();  //y:cart:uid:2018  2019

        //具体的redis map操作对象
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(key);

        hashOps.get(cart.getSkuId().toString());

        Long skuId = cart.getSkuId();

        Integer num = cart.getNum();
        Boolean bool = hashOps.hasKey(skuId.toString());
        //如果有改值，
        if (bool) {
            String cartJson = hashOps.get(skuId.toString()).toString();
            //得到具体的购物车对象
            cart = JsonUtils.parse(cartJson, Cart.class);
            //修改数量
            cart.setNum(cart.getNum() + num);
        } else {//如果没有直接添加
            cart.setUserId(user.getId());

            // 其它商品信息， 需要查询商品服务
            Sku sku = this.goodsClient.queryaSkuBySkuId(skuId);

            if (null == sku) {
                logger.error("要被加入购物车的商品不存在：skuId:{}", sku.getId());
            }

            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);

            cart.setPrice(sku.getPrice());

            cart.setOwnSpec(sku.getOwnSpec());

            cart.setTitle(sku.getTitle());

            cart.setNum(num);

        }
        hashOps.put(cart.getSkuId().toString(), JsonUtils.serialize(cart));
    }

    public List<Cart> queryCarts() {
        UserInfo user = LoginIntercepter.getUserInfo();
        //首先查询服务端是否已经包含这个sku的购物车信息
        //判断用户是否有购物车记录
        String key = KEY_PREFIX + user.getId();  //y:cart:uid:2018  2019
        if(!redisTemplate.hasKey(key)){
            return null;
        }
        //获取用户的购物车记录
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(key);

        List<Object> cartsJson = hashOps.values();

        //如果购物车集合为空
        if(CollectionUtils.isEmpty(cartsJson)){
            return null;
        }

        List<Cart> carts = new ArrayList<>();
        //把List<Object>集合转化为List<Cart>集合
        for (Object value : cartsJson) {
            Cart cart = JsonUtils.parse(value.toString(), Cart.class);
            carts.add(cart);
        }
        return carts;
    }

    public void updateNum(String skuId, Integer num) {
        //查询当前skuId对应的购物车对象，然后该具体的数值
        //改完后继续要存入redis

        // 获取登录用户
        UserInfo user = LoginIntercepter.getUserInfo();
        String key = KEY_PREFIX + user.getId();
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);
        // 获取购物车
        String json = hashOps.get(skuId.toString()).toString();
        Cart cart = JsonUtils.parse(json, Cart.class);
        cart.setNum(num);
        // 写入购物车
        hashOps.put(skuId.toString(), JsonUtils.serialize(cart));
    }

    public void deleteCartById(String skuId) {
        // 获取登录用户
        UserInfo user = LoginIntercepter.getUserInfo();
        String key = KEY_PREFIX + user.getId();
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);

        hashOps.delete(skuId);
    }
}
