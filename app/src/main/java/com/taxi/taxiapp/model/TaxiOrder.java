package com.taxi.taxiapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by kristina on 06.09.16.
 */
public class TaxiOrder implements Parcelable, Comparator<Date> {

    int id;
    StartAddress startAddress;
    EndAddress endAddress;
    Price price;
    String orderTime;
    Date date;
    Vehicle vehicle;

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StartAddress getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(StartAddress startAddress) {
        this.startAddress = startAddress;
    }

    public EndAddress getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(EndAddress endAddress) {
        this.endAddress = endAddress;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    protected TaxiOrder(Parcel in) {
        id = in.readInt();
        startAddress = (StartAddress) in.readValue(StartAddress.class.getClassLoader());
        endAddress = (EndAddress) in.readValue(EndAddress.class.getClassLoader());
        price = (Price) in.readValue(Price.class.getClassLoader());
        orderTime = in.readString();
        vehicle = (Vehicle) in.readValue(Vehicle.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeValue(startAddress);
        dest.writeValue(endAddress);
        dest.writeValue(price);
        dest.writeString(orderTime);
        dest.writeValue(vehicle);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TaxiOrder> CREATOR = new Parcelable.Creator<TaxiOrder>() {
        @Override
        public TaxiOrder createFromParcel(Parcel in) {
            return new TaxiOrder(in);
        }

        @Override
        public TaxiOrder[] newArray(int size) {
            return new TaxiOrder[size];
        }
    };

    @Override
    public int compare(Date date1, Date date2) {
        return date1.compareTo(date2);
    }
}
