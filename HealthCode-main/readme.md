# 健康码管理系统

## 环境要求

1. **Java**：JDK 17+
2. **Maven**
3. **数据库**：MySQL 8.0+
4. **缓存**：Redis
5. **消息队列**：kafka
6. **服务治理**：Nacos
7. **开发工具**：IntelliJ IDEA/Eclipse

## 项目配置

修改每个服务的配置文件
```yml
spring:
  # 数据库
  datasource:
    url: jdbc:mysql://localhost:3306/yout_database?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: yout_username
    password: your_password
  cloud:
    # nacos
    nacos:
      discovery:
        server-addr: localhost:8848
        namespace: public
  # Redis
  redis:
    host: localhost
    port: 6379
    password: your_redis_password
```

在user模块的配置文件中修改微信小程序配置
```yml
# 微信小程序配置
spring:
  wechat:
    appid: your_wx_app_id
    secret: your_wx_app_secret
```