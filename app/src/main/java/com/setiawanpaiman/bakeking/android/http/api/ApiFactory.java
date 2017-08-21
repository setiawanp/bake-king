package com.setiawanpaiman.bakeking.android.http.api;

import android.support.annotation.NonNull;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Setiawan Paiman on 21/8/17.
 */

public class ApiFactory {

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";

    @NonNull
    private final Retrofit mRetrofit;

    public ApiFactory() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build();
    }

    public <T> T create(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }
}
