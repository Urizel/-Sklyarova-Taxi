package com.taxi.taxiapp.model;

import com.google.gson.JsonArray;
import com.taxi.taxiapp.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by kristina on 06.09.16.
 */
public interface TaxiApi {

    @GET("orders.json")
    Observable<JsonArray> getTaxiOrders();

    @GET("images/{imageName}")
    Observable<ResponseBody> getImage(@Path("imageName") String imageName);

    class InitApiService {

        private static TaxiApi mTaxiService;
        private static Scheduler mSubscribeScheduler;

        public static TaxiApi getTaxiApi() {
            if (mTaxiService == null) {
                mTaxiService = TaxiApi.Factory.create();
            }
            return mTaxiService;
        }

        public static Scheduler subscribeScheduler() {
            if (mSubscribeScheduler == null) {
                mSubscribeScheduler = Schedulers.io();
            }
            return mSubscribeScheduler;
        }

    }

    class Factory {

        public static TaxiApi create() {

            OkHttpClient client = new OkHttpClient
                    .Builder()
                    .addNetworkInterceptor(chain -> {
                        Request.Builder builder = chain.request().newBuilder();
                        builder.addHeader("Accept", "application/json");
                        builder.addHeader("Content-type", "application/json; charset=UTF-8");

                        Request request = builder.build();
                        try {
                            Response response = chain.proceed(request);
                            return response;
                        } catch (Exception e) {
                            throw e;
                        }
                    })
                    .connectTimeout(180, TimeUnit.SECONDS)
                    .readTimeout(180, TimeUnit.SECONDS)
                    .build();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            return retrofit.create(TaxiApi.class);
        }

    }
}
