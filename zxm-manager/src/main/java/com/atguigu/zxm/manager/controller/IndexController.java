package com.atguigu.zxm.manager.controller;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import com.atguigu.zxm.manager.service.SysUserService;
import com.atguigu.zxm.manager.service.ValidateCodeService;
import com.atguigu.zxm.utils.AuthContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService ;

    @Autowired
    private ValidateCodeService validateCodeService;

    // 直接复制到 IndexController 类里
    @PutMapping(value = "/authorizations")
    public Result<?> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        // 这里写刷新 token 的逻辑（示例，按你实际业务改）
        return Result.build("新的 token", ResultCodeEnum.SUCCESS);
    }

    //用户退出
    @GetMapping(value = "/logout")
    public Result logout(@RequestHeader(value = "token") String token) {
        sysUserService.logout(token) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    //获取用户信息
    @GetMapping(value = "/getUserInfo")
    public Result<SysUser> getUserInfo() {
        return Result.build(AuthContextUtil.get(), ResultCodeEnum.SUCCESS) ;
    }

//    @GetMapping(value = "/getUserInfo")
//    public Result<SysUser> getUserInfo(@RequestHeader(name = "token") String token) {
//        SysUser sysUser = sysUserService.getUserInfo(token) ;
//        return Result.build(sysUser , ResultCodeEnum.SUCCESS) ;
//    }

    @Operation(summary = "登录接口")
    @PostMapping(value = "/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto) ;
        return Result.build(loginVo , ResultCodeEnum.SUCCESS) ;
    }


    //获取图形化验证码
    @GetMapping("/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo , ResultCodeEnum.SUCCESS) ;
    }

}