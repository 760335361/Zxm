package com.atguigu.zxm.manager.webService;

import com.atguigu.spzx.model.entity.user.UserInfo;
import jakarta.jws.WebService;
import jakarta.xml.ws.Endpoint;

import java.util.Date;
@WebService(
//        targetNamespace = "http://zxm" // 与接口一致
//        endpointInterface = "com.atguigu.zxm.manager.webService.getUserByUsername" // 完整接口路径
)
public class zxm {
    public String getUserByUsername(String username) {
        return "朱学敏";
    }

    public static void main(String[] args) {
        String address = "http://localhost:8081/zxm";
        Endpoint.publish(address, new zxm()); // Jakarta 版 Endpoint
        System.out.println("服务启动成功！WSDL：" + address + "?wsdl");
    }
}
