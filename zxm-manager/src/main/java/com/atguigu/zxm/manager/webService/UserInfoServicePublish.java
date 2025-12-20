package com.atguigu.zxm.manager.webService;
import com.atguigu.spzx.model.entity.user.UserInfo;
// 同样修正导包为 jakarta.jws.*
import jakarta.jws.WebService;
import jakarta.xml.ws.Endpoint; // 注意：Endpoint 导包还是 jakarta.xml.ws.Endpoint
import java.util.Date;

@WebService(
        targetNamespace = "http://ws.spzx.atguigu.com", // 与接口一致
        endpointInterface = "com.atguigu.zxm.manager.webService.UserInfoServicePublish" // 完整接口路径
)
public class UserInfoServicePublish  {


    public UserInfo getUserByUsername(String username) {
        UserInfo user = new UserInfo();
        user.setId(10001L);
        user.setUsername(username);
        user.setNickName("测试_" + username);
        user.setStatus(1);
        user.setLastLoginTime(new Date());
        return user;
    }


    public String addUser(UserInfo userInfo) {
        return userInfo.getUsername() != null ? "新增成功：" + userInfo.getUsername() : "用户名不能为空";
    }

    public static void main(String[] args) {
        String address = "http://localhost:8080/UserInfoServicePublis";
        Endpoint.publish(address, new UserInfoServicePublish()); // Jakarta 版 Endpoint
        System.out.println("服务启动成功！WSDL：" + address + "?wsdl");
    }
}
