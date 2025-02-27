package org.software.code.config;


import org.mybatis.spring.annotation.MapperScan;

/**
 * MyBatis 配置类，主要用于配置 MyBatis 映射器的扫描路径。
 * 通过使用 @MapperScan 注解，指定 MyBatis 需要扫描的包路径，
 * 使得 MyBatis 能够自动发现并注册该包下的所有映射器接口。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@MapperScan("org.software.code")
public class MybatisConfig {
  // 此类仅用于配置 MyBatis 映射器扫描路径，无具体逻辑代码
}
