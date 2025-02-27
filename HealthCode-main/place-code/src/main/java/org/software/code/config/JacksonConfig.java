package org.software.code.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * 配置类，全局配置 Jackson 序列化
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Configuration
public class JacksonConfig {

  // 定义日期格式化模式常量
  private static final String DATE_FORMAT = "yyyy-MM-dd";
  private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

  @Bean
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    ObjectMapper objectMapper = new ObjectMapper();

    // 禁用将日期序列化为时间戳的特性
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    // 忽略未知属性，避免反序列化时因未知属性而报错
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // 创建一个简单模块，用于处理自定义序列化
    SimpleModule module = new SimpleModule();
    // 将 Long 和 BigInteger 类型序列化为字符串
    module.addSerializer(Long.class, ToStringSerializer.instance);
    module.addSerializer(Long.TYPE, ToStringSerializer.instance);
    module.addSerializer(BigInteger.class, ToStringSerializer.instance);

    // 使用 DateTimeFormatter 进行日期格式化，它是线程安全的
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    // 注册 java.util.Date 类型的序列化器
    module.addSerializer(java.util.Date.class, new com.fasterxml.jackson.databind.ser.std.DateSerializer(
        false, new java.text.SimpleDateFormat(DATE_FORMAT)));

    // 注册 Java 8 日期时间类型的序列化器
    module.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
    module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));

    // 注册 Java 8 日期时间模块，支持更多 Java 8 日期时间类型
    objectMapper.registerModule(new JavaTimeModule());
    // 注册自定义模块
    objectMapper.registerModule(module);

    // 创建 MappingJackson2HttpMessageConverter 实例，并将配置好的 ObjectMapper 设置进去
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setObjectMapper(objectMapper);
    return converter;
  }
}