package com.atguigu.spzx.model.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户登录请求参数")
public class LoginDto {

    private String userName ;
    private String password ;
    private String captcha ;
    private String codeKey ;

}
