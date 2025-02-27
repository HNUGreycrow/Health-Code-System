package org.software.code.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.software.code.common.util.JWTUtil;
import org.software.code.entity.UserInfo;
import org.software.code.mapper.UserInfoMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@SpringBootTest
@MapperScan("org.software.code.mapper")
class UserControllerTest {

  @Resource
  private UserInfoMapper userInfoMapper;

  private static final String RootPath="D:\\codeProject\\HealthCode-main\\健康码";
  private static final String FileFormat=".png";

  private static final ThreadLocal<SimpleDateFormat> LOCALDATEFORMAT=ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMddHHmmss"));

  @Test
  void getUserByID() {
    long snowflakePid = IdUtil.getSnowflake().nextId();
    System.out.println("id：" + snowflakePid);
  }

  @Test
  void generateToken() {
    String uid = "1893933561999347712";
    System.out.println(JWTUtil.generateJWToken(Long.parseLong(uid), 3600000));
  }


  @Test
  void createManage() {
    // 创建 BCryptPasswordEncoder 实例
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 原始密码
    String rawPassword = "123456";

    // 进行加密
    String encodedPassword = passwordEncoder.encode(rawPassword);
    System.out.println("原始密码: " + rawPassword);
    System.out.println("加密后的密码: " + encodedPassword);

    // 验证密码
    boolean isMatch = passwordEncoder.matches(rawPassword, encodedPassword);
    System.out.println("密码验证结果: " + (isMatch ? "匹配" : "不匹配"));
  }

  @Test
  void createBarcode() {
    try {
      for (int i = 0; i < 10; i++) {
        Random random = new Random();
        int first = random.nextInt(3);
        StringBuilder id = new StringBuilder(String.valueOf(first));
        String snowId = String.valueOf(IdUtil.getSnowflake().nextId());
        // 雪花算法生成的id第一位换成first，即试管的类型
        id.append(snowId.substring(1));
        String filePath = "D:\\codeProject\\HealthCode-main\\试管条形码/" + id + ".png";
        generateBarcode(String.valueOf(id), filePath);
        System.out.println("条形码已生成并保存到: " + filePath);
      }
    } catch (WriterException | IOException e) {
      e.printStackTrace();
    }
  }

  public static void generateBarcode(String id, String filePath) throws WriterException, IOException {
    // 将 id 和 kind 组合成一个字符串，用特定分隔符（这里用 |）分隔
    int width = 300;
    int height = 100;
    BarcodeFormat format = BarcodeFormat.CODE_128;
    Map<EncodeHintType, Object> hints = new HashMap<>();
    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

    Code128Writer writer = new Code128Writer();
    BitMatrix matrix = writer.encode(id, format, width, height, hints);

    BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
    File outputFile = new File(filePath);
    ImageIO.write(image, "png", outputFile);
  }

  /**
   * 利用token生成健康码
   */
  @Test
  void generateQR() {
    List<UserInfo> userInfoList = userInfoMapper.selectList(new QueryWrapper<>());
    userInfoList.forEach(userInfo -> {
      long uid = userInfo.getUid();
      QRCodeUtil.createCodeToFile(JWTUtil.generateJWToken(uid, 100L * 365 * 24 * 60 * 60 * 1000),new File(RootPath),uid+FileFormat);
    });
  }
}