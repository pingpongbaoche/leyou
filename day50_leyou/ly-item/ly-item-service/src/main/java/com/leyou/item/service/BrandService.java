package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 分页查询
     */
    public PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页 分页助手会利用mybatis的拦截器对接下来要执行的sql进行拦截，自动拼接limit语句
        PageHelper.startPage(page, rows);

        //过滤 查询条件key不为空的话就过滤
        Example example = new Example(Brand.class);//可以利用反射得到Brand里面定义的属性
        if (StringUtils.isNotBlank(key)) {
            //过滤条件 select * from tb_brand where 'name' like "%xx%" or letter == 'xx'
            example.createCriteria().orLike("name", "%" + key + "%").
                    orEqualTo("letter", key.toUpperCase());
        }

        //排序 order by id desc
        if (StringUtils.isNotBlank(sortBy)) {
            String orderByClause = sortBy + (desc ? " DESC " : " ASC ");//注意要有空格
            example.setOrderByClause(orderByClause);
        }

        //查询
        List<Brand> brands = brandMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(brands)) {
            throw new LyException(ExceptionEnums.BRAND_NOT_FOUND);
        }

        //解析分页结果
        PageInfo<Brand> info = new PageInfo<>(brands);

        return new PageResult<>(info.getTotal(), brands);
    }
}
