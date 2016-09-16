package com.taxi.taxiapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kristina on 06.09.16.
 */
public class StartAddress implements Parcelable {

    String city;
    String address;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    protected StartAddress(Parcel in) {
        city = in.readString();
        address = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(city);
        dest.writeString(address);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<StartAddress> CREATOR = new Parcelable.Creator<StartAddress>() {
        @Override
        public StartAddress createFromParcel(Parcel in) {
            return new StartAddress(in);
        }

        @Override
        public StartAddress[] newArray(int size) {
            return new StartAddress[size];
        }
    };
}
