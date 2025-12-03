//package com.atguigu.zxm.manager.webService;
//import com.atguigu.spzx.model.entity.user.UserInfo;
//
//public class UserInfoClient {
//    public static void main(String[] args) {
//        // 1. 打开“服务说明书”，创建服务工厂（相当于客户端拿到你的小店联系方式）
//        UserInfoServiceService serviceFactory = new UserInfoServiceService();
//
//        // 2. 拿到服务实例（相当于客户端打通了和你小店的电话）
//        UserInfoService userService = serviceFactory.getUserInfoServicePort();
//
//        // 3. 调用你写的第一个方法：getUserByUsername（查用户）
//        System.out.println("===== 调用「查用户」方法 =====");
//        UserInfo user = userService.getUserByUsername("zhangsan"); // 传用户名“zhangsan”
//        System.out.println("调用结果：" + user); // 打印你服务端返回的用户信息
//
//        // 4. 调用你写的第二个方法：addUser（新增用户）
//        System.out.println("\n===== 调用「新增用户」方法 =====");
//        UserInfo newUser = new UserInfo(); // 新建一个用户对象
//        newUser.setUsername("lisi"); // 填用户名（必填）
//        newUser.setNickName("李四"); // 填昵称（可选）
//        String addResult = userService.addUser(newUser); // 传用户对象
//        System.out.println("调用结果：" + addResult); // 打印你服务端返回的“新增成功/失败”
//    }
//}