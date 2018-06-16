package com.weber.okex.ticker.client.fcoin.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FCoinSignUtil {

  public static String sign(
      String method, String url, String timestamp, Map params, String accessKey) {
    StringBuilder sb = new StringBuilder(500);
    method = Objects.requireNonNull(method).toUpperCase();
    sb.append(method).append(url).append(timestamp).append(extractPostBody(method, params));
    String concat = sb.toString();
    log.info(concat);
    byte[] encodeData = Base64.getEncoder().encode(concat.getBytes(StandardCharsets.UTF_8));
    return Base64.getEncoder()
        .encodeToString(hmacSha1(encodeData, accessKey.getBytes(StandardCharsets.UTF_8)));
  }

  public static String extractPostBody(String method, Map<String, String> params) {
    if (!"POST".equals(method)) {
      return "";
    }
    if (params.isEmpty()) {
      return "";
    }
    List<String> paramKeys = new LinkedList<>();

    for (Map.Entry<String, String> entry : params.entrySet()) {
      if (entry.getValue() == null) {
        return "";
      }
      if ((entry.getValue() instanceof String) && ((String) entry.getValue()).isEmpty()) {
        return "";
      }
      paramKeys.add(entry.getKey());
    }
    Collections.sort(paramKeys);
    StringBuilder sb = new StringBuilder(100);
    for (String paramKey : paramKeys) {
      sb.append(paramKey).append("=").append(params.get(paramKey)).append("&");
    }
    sb.deleteCharAt(sb.length() - 1); // 删除结尾多余的&
    return sb.toString();
  }

  public static byte[] hmacSha1(byte[] data, byte[] key) {
    try {
      String digestMethod = "HmacSHA1";
      SecretKeySpec signingKey = new SecretKeySpec(key, digestMethod);
      Mac mac = Mac.getInstance(digestMethod);
      mac.init(signingKey);
      return mac.doFinal(data);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("不支持的加密方式", e);
    } catch (InvalidKeyException e) {
      throw new RuntimeException("无效的私钥", e);
    }
  }
}
