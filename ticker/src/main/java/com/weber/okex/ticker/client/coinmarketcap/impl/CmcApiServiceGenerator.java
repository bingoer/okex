package com.weber.okex.ticker.client.coinmarketcap.impl;

import java.io.IOException;
import java.lang.annotation.Annotation;

import com.weber.okex.ticker.client.coinmarketcap.constants.CmcApiConstants;
import com.weber.okex.ticker.client.coinmarketcap.domain.CmcApiError;
import com.weber.okex.ticker.client.coinmarketcap.exception.CmcApiException;
import com.weber.okex.ticker.client.security.AuthenticationInterceptor;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Generates a API implementation based on @see {@link CmcApiService}.
 */
public class CmcApiServiceGenerator {

    static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
        new Retrofit.Builder()
            .baseUrl(CmcApiConstants.API_BASE_URL)
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
                CmcApiError apiError = getCmcApiError(response);
                throw new CmcApiException(apiError);
            }
        } catch (IOException e) {
            throw new CmcApiException(e);
        }
    }

    /**
     * Extracts and converts the response error body into an object.
     */
    public static CmcApiError getCmcApiError(Response<?> response) throws IOException, CmcApiException {
        return (CmcApiError)retrofit.responseBodyConverter(CmcApiError.class, new Annotation[0])
            .convert(response.errorBody());
    }
}