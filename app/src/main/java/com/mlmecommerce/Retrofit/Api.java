package com.mlmecommerce.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Api {

    public static String token = null;
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (retrofit == null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(50, TimeUnit.MINUTES)
                    .readTimeout(50, TimeUnit.MINUTES)
                    .addInterceptor(logging)
                    .retryOnConnectionFailure(true)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.41.222:9012/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient)
                    .build();

        }

        return retrofit;
    }

}
