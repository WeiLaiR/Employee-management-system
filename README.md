<h1 align="center">Employee-management-system</h1>
<h2 align="center">基于SpringBoot+SpringCloud+Vue开发的员工管理系统</h2>

## 简介

基于SpringBoot、SpringCloud和Vue Element-UI开发的前后端分离的员工管理系统。

多层加密保护重要数据安全(密码)。使用 jwt + 拦截器 + 自定义注解 完成用户认证、鉴权，且可在前端进行详细的权限控制。使用redis缓存频繁查询的数据以提升速度和降低数据库压力。服务间Feign通信。后端自定义的全局异常处理。前端统一处理异常......

目前仍在开发中，后续功能会尽快更新...

因项目变更为微服务项目，故部分模块功能、流程有所改变，README文档尚未更新！

* Server服务仍在拆分
* RSA加密模块已从Server工程中拆分出来
* 接入Gateway网关，认证鉴权略有变化！



## [依赖项](https://github.com/WeiLaiR/Employee-management-system/network/dependencies)

## 功能介绍

### 登录注册模块

用了很多加密，用以确保密码的安全。（项目拆分为微服务后逻辑略有变化！示例图尚未更新！）

![1_2](https://github.com/WeiLaiR/Employee-management-system/blob/master/image/1_2.png)

* 非对称加密密钥定期更新(目前代码中每小时更新一次)。
* 加盐处理可做到有规律的代码变得无规律
* 存在数据库中的密码已经过多层加密



注册流程与之相似，但是不会回传token，同时注册完成后会在服务区初始化用户所需的表。



### 主页模块

主页功能尚未完善！后续会添加动态数据到主页。



### 员工管理模块

* 员工信息分页查询、条件查询
* 可对员工部分信息进行更改
* 删除或批量删除员工数据

### 个人信息模块

* 员工可对部分个人信息进行查询、修改

### 权限管理

* 员工分页查询
* 可对每个员工设置详细的权限

现在的权限分的比较模糊，是按照页面模块分的，后面会细化！

### 打卡模块

已完成功能：

* 员工打卡(统计打卡时间，不可重复打卡等)
* 统计某时间段内打卡员工(比如统计当天打卡员工)

后续会添加统计未打卡员工功能，两个思路：

* 不改数据库：可将打卡人员id与其他表人员id对照，可使用二分查找以单次long(n)的时间复杂度，查找出未打卡人员，总时间复杂度nlong(n)，。
* 该数据库：新增一个Boolean字段来判断是否打卡，并将所有员工数据在0点插入数据库，并将新字段设置为未打卡，应该算是用空间换时间的办法了。



### 邮箱验证模块

在计划中，目前尚未开发！

* 更改注册流程，注册必须通过邮箱验证码验证！
* 可使用邮箱验证码登录



### 任务模块

在计划中，目前尚未开发！

* 更高权限的用户可对低权限用户或分组下发任务。



### 鉴权流程

使用 jwt+拦截器+自定义注解进行鉴权（项目拆分为微服务后逻辑略有变化！示例图尚未更新！）。

（加入了Gateway网关，部分鉴权流程在网关完成）

![6_1](https://github.com/WeiLaiR/Employee-management-system/blob/master/image/6_1.png)

总体流程可简化为上图。

我们有两个拦截器分别为：JwtInterceptor和AuthorityInterceptor

两个拦截器拥有不同的优先级，JwtInterceptor的优先级更高，所以先执行JwtInterceptor。

JwtInterceptor的主要功能：取出Header中的token => 判断token是否为空 => 解析token取出用户id => 在redis中通过用户id寻找token，并进行比对 => 放行

> 总的来说JwtInterceptor就是在校验token是否合法，其中任何一个条件不满足都会向前端返回异常。

AuthorityInterceptor的主要功能：取出Header中的token => 解析token取出用户id => 查询数据库获取该id对应用户的权限信息 => 获取自定义注解中的权限信息 => 比对权限 => 放行

> AuthorityInterceptor并不对token进行合法判断，因为它在JwtInterceptor内层，能执行到这里说明token一定合法。



## 前端页面展示

### 登录注册模块

界面：

![1_1](https://github.com/WeiLaiR/Employee-management-system/blob/master/image/1_1.jpg)





### 主页(目前并未完善！)

![2_1](https://github.com/WeiLaiR/Employee-management-system/blob/master/image/2_1.png)

一主页的假数据，目前并不完善(主要是不知道拿什么数据填充合适)



### 员工管理模块

![3_1](https://github.com/WeiLaiR/Employee-management-system/blob/master/image/3_1.png)

![3_2](https://github.com/WeiLaiR/Employee-management-system/blob/master/image/3_2.png)





### 个人信息模块

![4_1](https://github.com/WeiLaiR/Employee-management-system/blob/master/image/4_1.png)

没啥好说的，就是查询和修改。

### 权限管理模块

![5_1](https://github.com/WeiLaiR/Employee-management-system/blob/master/image/5_1.png)

![5_2](https://github.com/WeiLaiR/Employee-management-system/blob/master/image/5_2.png)





### 打卡模块

打卡模块前端页面还未完成，但是部分后端接口已经完成并测试了。后续会补上前端页面！
