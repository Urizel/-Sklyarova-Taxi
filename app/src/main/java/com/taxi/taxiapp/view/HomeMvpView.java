package com.taxi.taxiapp.view;

import com.taxi.taxiapp.model.TaxiOrder;

import java.util.List;

/**
 * Created by kristina on 05.09.16.
 */
public interface HomeMvpView extends MvpView {

    void setData(List<TaxiOrder> ordersList);

    void setError(String message);

}
