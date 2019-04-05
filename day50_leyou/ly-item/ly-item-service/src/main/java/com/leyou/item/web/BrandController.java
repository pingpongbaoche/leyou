package com.leyou.item.web;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 分页查询品牌
     * */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,//当前页
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,//每页行数量
            @RequestParam(value = "sortBy", required = false) String sortBy,//根据排序，可不传
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,//默认升序
            @RequestParam(value = "key", required = false) String key//查询条件
    ){
        //根据5个参数查询  分页查询
        PageResult<Brand> result = brandService.queryBrandByPage(page,rows,sortBy,desc,key);
        return ResponseEntity.ok(result);
    }

    /**
     * 新增品牌
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids") List<Long> cids){
        brandService.saveBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();//新增 如果有返回值，用body，没有返回值，用build
    }

}
