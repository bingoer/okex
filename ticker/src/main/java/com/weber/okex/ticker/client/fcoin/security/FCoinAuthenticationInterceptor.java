package com.weber.okex.ticker.client.fcoin.security;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import com.weber.okex.ticker.client.fcoin.constants.FCoinApiConstants;
import com.weber.okex.ticker.client.fcoin.util.FCoinSignUtil;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A request interceptor that injects the API Key Header into requests, and signs messages, whenever
 * required.
 */
public class FCoinAuthenticationInterceptor implements Interceptor {

  private final String apiKey;

  private final String secret;
  private final String method;
  private final String url;
  private final String timestamp;
  private final Map params;

  public FCoinAuthenticationInterceptor(String apiKey, String secret, String method, String url, String timestamp, Map params) {

    this.apiKey = apiKey;
    this.secret = secret;
    this.method = method;
    this.url = url;
    this.timestamp = timestamp;
    this.params = params;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request original = chain.request();
    Request.Builder newRequestBuilder = original.newBuilder();

    newRequestBuilder
        .removeHeader(FCoinApiConstants.API_KEY_HEADER)
        .removeHeader(FCoinApiConstants.API_SIGNATURE_HEADER)
        .removeHeader(FCoinApiConstants.API_TIMESTAMP_HEADER);

      newRequestBuilder.addHeader(FCoinApiConstants.API_KEY_HEADER, apiKey);
      newRequestBuilder.addHeader(FCoinApiConstants.API_SIGNATURE_HEADER, FCoinSignUtil.sign(method, url, timestamp, params, secret));
      newRequestBuilder.addHeader(FCoinApiConstants.API_TIMESTAMP_HEADER, timestamp);

    // Endpoint requires signing the payload
    if ("GET".equals(method) && !params.isEmpty()) {
      HttpUrl.Builder urlBuilder = original.url().newBuilder();
      for (Object key : params.keySet()) {
        urlBuilder.addQueryParameter((String) key,  (String)params.get(key));
      }
      newRequestBuilder.url(urlBuilder.build());
    }

    // Build new request after adding the necessary authentication information
    Request newRequest = newRequestBuilder.build();
    return chain.proceed(newRequest);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final FCoinAuthenticationInterceptor that = (FCoinAuthenticationInterceptor) o;
    return Objects.equals(apiKey, that.apiKey) && Objects.equals(secret, that.secret);
  }

  @Override
  public int hashCode() {
    return Objects.hash(apiKey, secret);
  }
}
