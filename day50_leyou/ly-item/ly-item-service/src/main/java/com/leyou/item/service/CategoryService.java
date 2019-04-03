package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    //根据parentId查询Category
    public List<Category> queryCategoryListByPid(Long pid) {
        //查询条件，mapper会把对象中的非空属性作为查询条件
        Category t = new Category();
        t.setParentId(pid);
        List<Category> list = categoryMapper.select(t);//把Category中的非空字段作为查询的条件参数 select..where parent_id=...
        //判断结果
//        if (list == null || list.isEmpty())
        if(CollectionUtils.isEmpty(list))
        {
            throw new LyException(ExceptionEnums.CATEGORY_NOT_FOUND);
        }
        return list;
    }
}
