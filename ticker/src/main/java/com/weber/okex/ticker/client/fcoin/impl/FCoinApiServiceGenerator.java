package com.weber.okex.ticker.client.fcoin.impl;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.weber.okex.ticker.client.fcoin.constants.FCoinApiConstants;
import com.weber.okex.ticker.client.fcoin.domain.FCoinApiError;
import com.weber.okex.ticker.client.fcoin.exception.FCoinApiException;
import com.weber.okex.ticker.client.fcoin.security.FCoinAuthenticationInterceptor;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Generates a API implementation based on @see {@link FCoinApiService}.
 */
public class FCoinApiServiceGenerator {

    static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS);

    private static Retrofit.Builder builder =
        new Retrofit.Builder()
            .baseUrl(FCoinApiConstants.API_BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null, null, null, null, null);
    }

    public static <S> S createService(Class<S> serviceClass, String apiKey, String secret, String method, String url, String timestamp, Map params) {
        if (!StringUtils.isEmpty(apiKey) && !StringUtils.isEmpty(secret)) {
            httpClient.interceptors().clear();
            FCoinAuthenticationInterceptor interceptor = new FCoinAuthenticationInterceptor(apiKey, secret, method,  url, timestamp, params);
            httpClient.addInterceptor(interceptor);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }

    /**
     * Execute a REST call and block until the response is received.
     */
    public static <T> T executeSync(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                FCoinApiError apiError = getApiError(response);
                throw new FCoinApiException(apiError);
            }
        } catch (IOException e) {
            throw new FCoinApiException(e);
        }
    }

    /**
     * Extracts and converts the response error body into an object.
     */
    public static FCoinApiError getApiError(Response<?> response) throws IOException, FCoinApiException {
        return (FCoinApiError)retrofit.responseBodyConverter(FCoinApiError.class, new Annotation[0])
            .convert(response.errorBody());
    }
}