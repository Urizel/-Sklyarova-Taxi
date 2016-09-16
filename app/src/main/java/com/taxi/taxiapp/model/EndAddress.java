package com.taxi.taxiapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kristina on 06.09.16.
 */
public class EndAddress implements Parcelable {

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

    protected EndAddress(Parcel in) {
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
    public static final Parcelable.Creator<EndAddress> CREATOR = new Parcelable.Creator<EndAddress>() {
        @Override
        public EndAddress createFromParcel(Parcel in) {
            return new EndAddress(in);
        }

        @Override
        public EndAddress[] newArray(int size) {
            return new EndAddress[size];
        }
    };
}
