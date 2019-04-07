package com.leyou.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 指定上传的属性 从yaml中获取
 * */

@Data
@ConfigurationProperties(prefix = "ly.upload")//读取yaml的ly:upload:
public class UploadProperties {
    private String baseUrl;
    private List<String> allowTypes;
}
