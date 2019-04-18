package com.leyou.search.pojo;
import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult extends PageResult<Goods> {
    private List<Category> categories;// 分类待选项
    private List<Brand> brands;// 品牌待选项
    private List<Map<String,Object>> specs;// 规格参数过滤条件 规格参数的key及待选项

//    public SearchResult(Long total, Long totalPage, List<Goods> items, List<Category> categories, List<Brand> brands) {
//        super(total, totalPage, items);
//        this.categories = categories;
//        this.brands = brands;
//    }

    public SearchResult(Long total, Long totalPage, List<Goods> items, List<Category> categories, List<Brand> brands, List<Map<String, Object>> specs) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }
}
