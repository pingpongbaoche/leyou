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
     * 根据组id查询商品规格参数
     * */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParamByCid(@RequestParam("gid") Long gid) {
        return ResponseEntity.ok(specService.queryParamByCid(gid));
    }

}
