package com.leyou.item.api;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 被远程调用查询商品规格
 * */
public interface SpecificationApi {
    /**
     * 根据组id或商品分类id查询商品规格参数的集合
     * */
    @GetMapping("spec/params")
    List<SpecParam> queryParamList(
            @RequestParam(value = "gid", required = false) Long gid,//组id
            @RequestParam(value = "cid",required = false) Long cid,//分类id
            @RequestParam(value = "searching",required = false) Boolean searching //根据 要不要进行搜索 查询
    );

    @GetMapping("spec/group")
    List<SpecGroup> queryGroupByCid(@RequestParam("cid") Long cid);
}
