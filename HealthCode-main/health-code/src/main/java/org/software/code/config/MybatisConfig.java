package org.software.code.config;

import org.mybatis.spring.annotation.MapperScan;

/**
 * MybatisConfig 是一个用于配置 MyBatis 的类。
 * 在 Spring Boot 项目中，当使用 MyBatis 作为持久层框架时，需要配置 MyBatis 的映射器（Mapper）扫描路径。
 * 该类通过 @MapperScan 注解指定了 MyBatis 要扫描的包路径，使得 MyBatis 能够自动发现并注册这些包下的 Mapper 接口。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
// @MapperScan 注解用于指定 MyBatis 要扫描的包路径，Spring Boot 会自动在这些路径下查找 Mapper 接口，
// 并为这些接口创建代理实现，从而可以在代码中直接使用这些 Mapper 接口进行数据库操作。
// 这里指定扫描 "org.software.code" 包及其子包下的所有 Mapper 接口。
@MapperScan("org.software.code")
public class MybatisConfig {
    // 该类目前没有具体的逻辑代码，主要作用是通过 @MapperScan 注解完成 MyBatis Mapper 接口的扫描配置。
    // 后续如果需要添加其他的 MyBatis 相关配置，可以在该类中添加相应的方法和注解。
}