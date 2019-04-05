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
    BRAND_NOT_FOUND(404,"品牌不存在"),
    BRAND_SAVE_ERROR(500,"新增品牌失败"),
    UPLOAD_ERROR(500,"文件上传失败"),
    INVALID_FILE_TYPE(400,"无效的文件类型"),
    ;
    private int code;//异常状态码
    private String msg;//异常提示

}
