package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 异常提示枚举
 * 含异常状态码code和异常提示信息msg
 * */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum  ExceptionEnums {

    PRICE_CANNOT_BE_NULL(400,"价格不能为空！"),
    CATEGORY_NOT_FOUND(404,"商品分类没查到"),
    ;
    private int code;//异常状态码
    private String msg;//异常提示

}
