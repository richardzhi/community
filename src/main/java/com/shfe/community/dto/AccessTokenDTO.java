package com.shfe.community.dto;

import lombok.Data;

/*
* DTO 为数据传输对象，封装远程调用的参数
* */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
