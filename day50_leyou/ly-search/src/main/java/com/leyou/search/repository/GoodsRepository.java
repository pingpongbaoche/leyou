package com.leyou.search.repository;

import com.leyou.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 方便增删改查
 * */
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {
}
