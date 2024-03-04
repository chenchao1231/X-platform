# X-Platform
[![AUR](https://img.shields.io/badge/GPL-v3-red)](https://github.com/chenchao1231/X-platform/blob/main/License)
![GitHub stars](https://img.shields.io/github/stars/Chenchao1231/X-platform.svg?style=social&label=Stars)
![GitHub forks](https://img.shields.io/github/forks/Chenchao1231/X-platform.svg?style=social&label=Fork)


### 项目简介 
- [x] 代码拥有详细注释 无复杂逻辑 核心使用 SpringBoot 2.4.8
- [x] JWT / 基于Redis可配置单设备登录Token交互 任意切换 提供开放平台、OAuth2认证中心 支持点单登录
- [x] JPA + Mybatis-Plus 任意切换
- [x] 操作日志记录方式任意切换Mysql或Elasticseach记录
- [x] Java、Vue、SQL代码生成效率翻四倍
- [x] 动态权限管理、多维度轻松控制权限按钮显示、数据权限管理
- [x] 支持社交账号、短信等多方式登录 不干涉原用户数据 实现第三方账号管理
- [x] 基于Websocket消息推送管理、基于Quartz定时任务管理、数据字典管理
- [x] 后台提供分布式限流、同步锁、验证码等工具类 前端提供丰富Vue模版
- [x] 可动态配置短信、邮件、Vaptcha验证码等
- [x] 为什么要前后端分离
    - 都什么时代了还在用JQuery？ 

![](https://ooo.0o0.ooo/2019/04/29/5cc70cac4b7a4.png)

### 系统架构

<img src="https://ooo.0o0.ooo/2019/05/01/5cc87695f109d.png" width="600px"/>

### 后端所用技术

<img src=https://ooo.0o0.ooo/2019/11/25/gUiynavBAHd6hY8.jpg width=1000/>

##### 各框架依赖版本皆使用目前最新版本
- Spring Boot
- SpringMVC
- Spring Security
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/2.2.2.RELEASE/reference/html/)
- [MyBatis-Plus](http://mp.baomidou.com)：已更新至3.x版本
- [Druid](http://druid.io/)：阿里高性能数据库连接池（偏监控 注重性能可使用默认HikariCP） [Druid配置官方中文文档](https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter)
- [Json Web Token(JWT)](https://jwt.io/)
- [Quartz](http://www.quartz-scheduler.org)：定时任务
- [Beetl](http://ibeetl.com/guide/#beetl)：模版引擎 代码生成使用
- [Thymeleaf](https://www.thymeleaf.org/)：发送模版邮件使用
- [Hutool](http://hutool.mydoc.io/)：Java工具包
- [Jasypt](https://github.com/ulisesbocchio/jasypt-spring-boot)：配置文件加密(thymeleaf作者开发)
- MySQL
- 第三方SDK或服务
    - [七牛云文件存储服务](https://developer.qiniu.com/kodo/sdk/1239/java)
    - [腾讯位置服务](https://lbs.qq.com/webservice_v1/guide-ip.html)：需申请填入key后免费使用
    - 完整版
        - [Vaptcha人机验证码](https://www.vaptcha.com/)
        - [阿里云短信服务](https://dysms.console.aliyun.com)
- 其它开发工具
    - [Lombok](https://projectlombok.org/)
    - [阿里JAVA开发规约插件](https://github.com/alibaba/p3c)

### 最新最全面在线文档

> 第一时间更新，文档永不收费





### [分布式扩展](https://github.com/alibaba/dubbo-spring-boot-starter/blob/master/README_zh.md)

### XBoot后端学习分享（更新中）

### Docker下后端集群部署(更新中)

1.基于PXC架构Mysql数据库集群搭建

2.Redis集群搭建

3.Elasticsearch集群搭建

4.XBoot后端集群部署


