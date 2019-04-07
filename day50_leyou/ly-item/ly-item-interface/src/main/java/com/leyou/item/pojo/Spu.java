package com.leyou.item.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * SPU实体类
 * */
@Data
@Table(name = "tb_spu")
public class Spu {
    @Id
    @KeySql(useGeneratedKeys = true)//自增主键
    private Long id;
    private Long brandId;
    private Long cid1;// 1级类目
    private Long cid2;// 2级类目
    private Long cid3;// 3级类目
    private String title;// 标题
    private String subTitle;// 子标题
    private Boolean saleable;// 是否上架

    @JsonIgnore//返回页面时忽略的字段
    private Boolean valid;// 是否有效，逻辑删除用
    private Date createTime;// 创建时间

    @JsonIgnore //返回页面时忽略的字段
    private Date lastUpdateTime;// 最后修改时间

    //其实下面非数据库字段不应该是pojo中，应该是视图对象vo中才加 vo用于前端显示
    @Transient//不是数据库字段
    private String cname;//分类名称
    @Transient//不是数据库字段
    private String bname;//品牌名称



}
