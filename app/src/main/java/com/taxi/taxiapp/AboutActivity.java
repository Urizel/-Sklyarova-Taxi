package com.taxi.taxiapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.taxi.taxiapp.model.TaxiOrder;
import com.taxi.taxiapp.presenter.HomePresenter;
import com.taxi.taxiapp.presenter.TaxiInfoPresenter;
import com.taxi.taxiapp.view.AboutMvpView;
import com.taxi.taxiapp.view.HomeMvpView;

/**
 * Created by kristina on 06.09.16.
 */
public class AboutActivity extends AppCompatActivity implements AboutMvpView {

    private TaxiInfoPresenter mPresenter;
    private ProgressBar mProgress;
    private TaxiOrder mTaxiOrder;
    private ImageView mCarImage;
    private TextView mDate;
    private TextView mFrom;
    private TextView mTo;
    private TextView mAmount;
    private TextView mDriverName;
    private TextView mCarModel;
    private TextView mRegNumber;
    private Toolbar mToolbar;

    private static final String ORDER = "order";

    public static void startActivity(Context context, TaxiOrder order) {
        context.startActivity(new Intent(context, AboutActivity.class).putExtra(ORDER, order));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.additional_info));

        mTaxiOrder = getIntent().getParcelableExtra(ORDER);

        mPresenter = new TaxiInfoPresenter();
        mPresenter.attachView(this);

        mProgress = (ProgressBar)findViewById(R.id.progress);
        mCarImage = (ImageView)findViewById(R.id.ivCar);
        mDate = (TextView)findViewById(R.id.tvDate);
        mFrom = (TextView)findViewById(R.id.tvFrom);
        mTo = (TextView)findViewById(R.id.tvTo);
        mAmount = (TextView)findViewById(R.id.tvAmount);
        mDriverName = (TextView)findViewById(R.id.tvDriverName);
        mCarModel = (TextView)findViewById(R.id.tvModelName);
        mRegNumber = (TextView)findViewById(R.id.tvRegNumber);

        mPresenter.getTaxiOrderInfo(mTaxiOrder, this);
        setLocalData();
    }

    private void setLocalData(){
        mDate.setText(mTaxiOrder.getOrderTime());
        mFrom.setText(mTaxiOrder.getStartAddress().getAddress());
        mTo.setText(mTaxiOrder.getEndAddress().getAddress());
        mAmount.setText(mTaxiOrder.getPrice().getRefactorAmount());
        mDriverName.setText(mTaxiOrder.getVehicle().getDriverName());
        mCarModel.setText(mTaxiOrder.getVehicle().getModelName());
        mRegNumber.setText(mTaxiOrder.getVehicle().getRegNumber());
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void startDownload() {
        mCarImage.setVisibility(View.GONE);
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void setImage(Bitmap image) {
        mCarImage.setImageBitmap(image);
        mCarImage.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.GONE);
    }

}
