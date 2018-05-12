package com.weber.okex.ticker.client.impl;

import java.io.IOException;
import java.lang.annotation.Annotation;

import com.weber.okex.ticker.client.constants.OkexApiConstants;
import com.weber.okex.ticker.client.domain.OkexApiError;
import com.weber.okex.ticker.client.exception.OkexApiException;
import com.weber.okex.ticker.client.security.AuthenticationInterceptor;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Generates a API implementation based on @see {@link OkexApiService}.
 */
public class OkexApiServiceGenerator {

    static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
        new Retrofit.Builder()
            .baseUrl(OkexApiConstants.API_BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }

    public static <S> S createService(Class<S> serviceClass, String apiKey, String secret) {
        if (!StringUtils.isEmpty(apiKey) && !StringUtils.isEmpty(secret)) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(apiKey, secret);
            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);
                builder.client(httpClient.build());
                retrofit = builder.build();
            }
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
                OkexApiError apiError = getOkexApiError(response);
                throw new OkexApiException(apiError);
            }
        } catch (IOException e) {
            throw new OkexApiException(e);
        }
    }

    /**
     * Extracts and converts the response error body into an object.
     */
    public static OkexApiError getOkexApiError(Response<?> response) throws IOException, OkexApiException {
        return (OkexApiError)retrofit.responseBodyConverter(OkexApiError.class, new Annotation[0])
            .convert(response.errorBody());
    }
}