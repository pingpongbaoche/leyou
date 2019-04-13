package com.leyou.item.mapper;
import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {

    //新增中间表的sql
    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertCategoryBrand(@Param("cid") Long cid, @Param("bid") Long bid);

    //查询当前分类下的所有品牌
    @Select("SELECT b.* FROM tb_category_brand cb " +
            "INNER JOIN tb_brand b ON b.id = cb.brand_id where cb.category_id=#{cid} ")
    List<Brand> queryByCategoryId(@Param("cid") Long cid);
}
