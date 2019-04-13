package com.leyou.item.web;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specService;

    /**
     * 根据分类cid查询规格组
     */
    @GetMapping("groups/{cid}")//cid是商品分类id
    public ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable("cid") Long cid) {
        return ResponseEntity.ok(specService.queryGroupByCid(cid));
    }

    /**
     * 根据组id或商品分类id查询商品规格参数的集合
     * */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParamList(
            @RequestParam(value = "gid", required = false) Long gid,//组id
            @RequestParam(value = "cid",required = false) Long cid,//分类id
            @RequestParam(value = "searching",required = false) Boolean searching //根据 要不要进行搜索 查询
    ) {
        return ResponseEntity.ok(specService.queryParamList(gid, cid, searching));
    }



}
