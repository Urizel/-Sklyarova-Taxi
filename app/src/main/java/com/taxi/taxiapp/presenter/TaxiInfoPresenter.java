package com.taxi.taxiapp.presenter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.taxi.taxiapp.model.TaxiApi;
import com.taxi.taxiapp.model.TaxiOrder;
import com.taxi.taxiapp.view.AboutMvpView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class TaxiInfoPresenter implements Presenter<AboutMvpView> {

    private AboutMvpView mAboutMvpView;
    private Subscription mSubscription;
    private Observable<Bitmap> mObservableDownloadedBitmap;

    @Override
    public void attachView(AboutMvpView view) {
        this.mAboutMvpView = view;
    }

    @Override
    public void detachView() {
        this.mAboutMvpView = null;
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void getTaxiOrderInfo(TaxiOrder taxiOrder, Context context) {
        mAboutMvpView.startDownload();

        Subscriber<Bitmap> subscriber = new Subscriber<Bitmap>() {
            @Override
            public void onNext(Bitmap bitmap) {
                mAboutMvpView.setImage(bitmap);
            }
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
        };

        Observable<Bitmap> observBitmap = Observable
                .concat(Observable.just(getImageFromCache(context, taxiOrder.getVehicle().getPhoto())), downloadImage(context, taxiOrder.getVehicle().getPhoto()))
                .first(bitmap -> bitmap != null)
                .observeOn(AndroidSchedulers.mainThread());
        observBitmap.subscribe(subscriber);

    }

    public Observable<Bitmap> downloadImage(Context context, String imageId) {
        TaxiApi taxiApiService = TaxiApi.InitApiService.getTaxiApi();
        mObservableDownloadedBitmap = taxiApiService.getImage(imageId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(TaxiApi.InitApiService.subscribeScheduler())
                .map(responseBody -> {
                    Bitmap bitmap = BitmapFactory.decodeStream(responseBody.byteStream());
                    saveImageToDisk(bitmap, context, imageId);
                    return bitmap;
                });

        return mObservableDownloadedBitmap;
    }

    private Bitmap getImageFromCache(Context context, String fileName) {
        String fname = new File(context.getFilesDir(), fileName).getAbsolutePath();
        return BitmapFactory.decodeFile(fname);
    }

    private void saveImageToDisk(Bitmap bitmap, Context context, String fileName) {
        File image = new File(context.getFilesDir() + File.separator + fileName);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
