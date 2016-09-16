package com.taxi.taxiapp.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taxi.taxiapp.model.TaxiApi;
import com.taxi.taxiapp.model.TaxiOrder;
import com.taxi.taxiapp.utils.Utils;
import com.taxi.taxiapp.view.HomeMvpView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by kristina on 06.09.16.
 */
public class HomePresenter implements Presenter<HomeMvpView> {

    private HomeMvpView mHomeMvpView;
    private Subscription mSubscription;

    @Override
    public void attachView(HomeMvpView view) {
        this.mHomeMvpView = view;
    }

    @Override
    public void detachView() {
        this.mHomeMvpView = null;
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void getCarList() {

        mHomeMvpView.startDownload();
        TaxiApi taxiApiService = TaxiApi.InitApiService.getTaxiApi();
        mSubscription = taxiApiService.getTaxiOrders()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(TaxiApi.InitApiService.subscribeScheduler())
                .map(jsonElements -> {
                    Gson gson = new Gson();
                    List<TaxiOrder> orderList = gson.fromJson(jsonElements, new TypeToken<List<TaxiOrder>>() {
                    }.getType());
                    for (TaxiOrder order : orderList) {
                        order.getPrice().setRefactorAmount(Utils.convertAmount(order.getPrice().getAmount()));

                        SimpleDateFormat oldDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
                        SimpleDateFormat newDate = new SimpleDateFormat("dd.MM.yyyy HH.mm", Locale.US);
                        Date date = null;
                        try {
                            date = oldDate.parse(order.getOrderTime());
                            order.setDate(date);
                            String formattedDate = newDate.format(date);
                            order.setOrderTime(formattedDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    return orderList;
                })
                .subscribe(new Subscriber<List<TaxiOrder>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable error) {
                        mHomeMvpView.setError(error.getMessage());
                    }

                    @Override
                    public void onNext(List<TaxiOrder> data) {
                        mHomeMvpView.setData(data);
                    }
                });
    }

}
