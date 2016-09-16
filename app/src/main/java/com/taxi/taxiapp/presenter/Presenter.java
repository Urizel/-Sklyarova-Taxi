package com.taxi.taxiapp.presenter;

/**
 * Created by kristina on 05.09.16.
 */

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
