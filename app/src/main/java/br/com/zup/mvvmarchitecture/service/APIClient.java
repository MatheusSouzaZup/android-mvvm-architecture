package br.com.zup.mvvmarchitecture.service;

import android.support.annotation.NonNull;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;


import br.com.zup.mvvmarchitecture.MyApplication;
import br.com.zup.mvvmarchitecture.util.Utils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIClient {
    private static APIClient mInstance;

    public synchronized static APIClient getInstance() {
        return mInstance;
    }

    private Retrofit mRetrofit;
    private OkHttpClient mClient;

    public APIClient(String baseUrl) {
        //todo change for the buildconfig
        mInstance = this;
        mClient = getClient();

        GsonBuilder builder = new GsonBuilder();

        mRetrofit = new Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .client(mClient)
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .build();
    }

    private OkHttpClient getClient() {
        return new OkHttpClient()
                .newBuilder()
                .addInterceptor(checkConnectionInterceptor)
                .addInterceptor(requestIntercept)
                .addInterceptor(getLoggingCapableHttpClient())
                .connectTimeout(90, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .build();
    }


    private final Interceptor checkConnectionInterceptor = chain -> {
        if (!Utils.isOnline(MyApplication.getInstance())) {
            throw new NoConnectionException();
        }
        return chain.proceed(chain.request());
    };

    private HttpLoggingInterceptor getLoggingCapableHttpClient() {
        HttpLoggingInterceptor mLogging = new HttpLoggingInterceptor();
        mLogging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return mLogging;
    }

    private final Interceptor requestIntercept = chain -> {

        final Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder();
        requestBuilder.addHeader("location", "udi");
        requestBuilder.addHeader("x-application-key", "4fce920056ef013646ad000d3ac06d76");
        final Request request = requestBuilder.build();

        return chain.proceed(request);
    };

    public void cancelAllRequests() {
        getInstance().mClient.dispatcher().cancelAll();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public OkHttpClient getOkHttpClient() {
        return mClient;
    }

}


