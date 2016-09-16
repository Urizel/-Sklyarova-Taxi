package com.taxi.taxiapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.taxi.taxiapp.adapters.TaxiOrdersListAdapter;
import com.taxi.taxiapp.model.TaxiOrder;
import com.taxi.taxiapp.presenter.HomePresenter;
import com.taxi.taxiapp.view.HomeMvpView;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity implements HomeMvpView {

    private HomePresenter mPresenter;
    private RecyclerView mTaxiOrderList;
    private ProgressBar mProgress;
    private TaxiOrdersListAdapter mAdapter;
    private ScheduledExecutorService mService;
    private final long PERIOD_TIME_TO_CLEAR_CACHE = 1;
    private final long TIME_TO_SAVE_CACHE = 1000 * 60 * 10;
    private LinearLayout mRootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mPresenter = new HomePresenter();
        mPresenter.attachView(this);

        mTaxiOrderList = (RecyclerView) findViewById(R.id.rvTaxiOrders);
        mProgress = (ProgressBar)findViewById(R.id.progress);
        mRootLayout = (LinearLayout)findViewById(R.id.root);

        mAdapter = new TaxiOrdersListAdapter(this);
        mAdapter.setCallback(taxiOrder -> AboutActivity.startActivity(HomeActivity.this, taxiOrder));
        mTaxiOrderList.setAdapter(mAdapter);
        mTaxiOrderList.setLayoutManager(new LinearLayoutManager(this));

        mPresenter.getCarList();

        mService = Executors.newSingleThreadScheduledExecutor();
        mService.scheduleWithFixedDelay((Runnable) () -> {
            File f = new File(getFilesDir().toString());
            File file[] = f.listFiles();
            if(file.length > 0){
                for(int i = 0; i < file.length; i++){
                    long currentTime = new Date().getTime();
                    long saveFileTime = file[i].lastModified();
                    if((currentTime - saveFileTime) > TIME_TO_SAVE_CACHE){
                        file[i].delete();
                    }
                }
            }

        }, 0, PERIOD_TIME_TO_CLEAR_CACHE, TimeUnit.SECONDS);

    }


    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        mService.shutdown();
        super.onDestroy();
    }


    @Override
    public void startDownload() {
        mProgress.setVisibility(View.VISIBLE);
        mTaxiOrderList.setVisibility(View.GONE);
    }

    @Override
    public void setData(List<TaxiOrder> ordersList) {
        mAdapter = (TaxiOrdersListAdapter) mTaxiOrderList.getAdapter();
        mAdapter.setTaxiOrders(ordersList);
        mAdapter.notifyDataSetChanged();
        mProgress.setVisibility(View.GONE);
        mTaxiOrderList.setVisibility(View.VISIBLE);
    }

    @Override
    public void setError(String message) {
        mProgress.setVisibility(View.GONE);
        Snackbar.make(mRootLayout, message, Snackbar.LENGTH_SHORT).show();
    }
}
