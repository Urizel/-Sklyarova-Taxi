package com.taxi.taxiapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kristina on 06.09.16.
 */
public class Price implements Parcelable {

    int amount;
    String currency;
    String refactorAmount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRefactorAmount() {
        return refactorAmount;
    }

    public void setRefactorAmount(String refactorAmount) {
        this.refactorAmount = refactorAmount;
    }

    protected Price(Parcel in) {
        amount = in.readInt();
        currency = in.readString();
        refactorAmount = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(amount);
        dest.writeString(currency);
        dest.writeString(refactorAmount);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Price> CREATOR = new Parcelable.Creator<Price>() {
        @Override
        public Price createFromParcel(Parcel in) {
            return new Price(in);
        }

        @Override
        public Price[] newArray(int size) {
            return new Price[size];
        }
    };
}
