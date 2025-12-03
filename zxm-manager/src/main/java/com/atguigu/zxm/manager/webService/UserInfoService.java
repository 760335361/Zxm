package com.atguigu.zxm.manager.webService;
import com.atguigu.spzx.model.entity.user.UserInfo;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
// 命名空间用项目包名反向，避免冲突

@WebService(targetNamespace = "http://ws.spzx.atguigu.com")
public interface UserInfoService {

    // 1. 根据用户名查询用户（简单参数+返回复杂对象）
    @WebMethod
    UserInfo getUserByUsername(String username);

    // 2. 新增用户（接收复杂对象参数，返回结果提示）
    @WebMethod
    String addUser(UserInfo userInfo);
}