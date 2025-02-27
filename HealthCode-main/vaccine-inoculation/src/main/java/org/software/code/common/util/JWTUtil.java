package org.software.code.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.software.code.common.except.BusinessException;
import org.software.code.common.except.ExceptionEnum;

import java.util.Date;

/**
 * JWTUtil 类用于生成和解析 JSON Web Token (JWT)。
 * JWT 是一种开放标准 (RFC 7519)，它定义了一种紧凑且自包含的方式，用于在各方之间安全地传输信息。
 * 该类提供了生成 JWT Token 和从 JWT Token 中提取用户 ID 的功能。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
public class JWTUtil {
    // 日志记录器，用于记录与 JWT 操作相关的日志信息
    private static final Logger logger = LogManager.getLogger(JWTUtil.class);
    // JWT 的签名密钥，用于对 JWT 进行签名和验证
    private static String secretKey = "secret_key";

    /**
     * 生成 JWT Token 的方法。
     *
     * @param id             用户的唯一标识符，通常为用户 ID，会被存储在 JWT 的 subject 字段中。
     * @param expirationTime Token 的过期时间，单位为毫秒，从当前时间开始计算。
     * @return 生成的 JWT Token 字符串。
     */
    public static String generateJWToken(long id, long expirationTime) {
        // 生成 JWT Token
        // 设置 JWT 的 subject 字段为用户 ID 的字符串表示
        // 设置 JWT 的签发时间为当前时间
        // 设置 JWT 的过期时间为当前时间加上指定的过期时间
        // 使用 HS256 算法和指定的密钥对 JWT 进行签名
        // 构建并生成最终的 JWT Token 字符串
        String qrcode_token = Jwts.builder()
            .setSubject(Long.toString(id))
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
        return qrcode_token;
    }

    /**
     * 从 JWT Token 中提取用户 ID 的方法。
     *
     * @param token 待解析的 JWT Token 字符串。
     * @return 提取出的用户 ID。
     * @throws BusinessException 如果解析过程中出现异常，例如 Token 过期、签名验证失败等，会抛出该异常。
     */
    public static long extractID(String token) throws NullPointerException {
        try {
            // 使用指定的密钥对 JWT Token 进行解析，获取其声明信息
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            // 从声明信息中获取 subject 字段，并将其转换为长整型作为用户 ID 返回
            return Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            // 记录解析失败的日志信息，包括 Token 内容和异常信息
            logger.error("Failed to extract ID from token: {}, error: {}", token, e.getMessage());
            // 抛出业务异常，表示 Token 已过期
            throw new BusinessException(ExceptionEnum.TOKEN_EXPIRED);
        }
    }
}