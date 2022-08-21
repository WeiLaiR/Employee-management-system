<h1 align="center">Employee-management-system</h1>
<h2 align="center">基于SpringBoot+Vue开发的员工管理系统</h2>

## 简介

基于SpringBoot和Vue饿了么UI(Element-UI)开发的前后端分离的员工管理系统，或者它可以成为任何后端管理系统。

整个系统别的不多，就是加密多，用了很多必要的加密手段来确保关键数据(密码)安全："简单"的加盐、RAS加密、SHA2-256加密、HMAC-SHA256加密、SHA3-256加密。使用jwt+手写拦截器完成系统的鉴权。使用redis缓存频繁查询的数据以提升速度和降低数据库压力。后端自定义的全局异常处理。前端统一处理异常......

目前仍在开发中，后续功能会快速更新...

## [依赖项](https://github.com/WeiLaiR/Employee-management-system/network/dependencies)

## 功能详解

### 登录流程



