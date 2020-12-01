package com.example.yeol.jolup1;

import android.os.Parcel;
import android.os.Parcelable;

import static java.lang.System.in;

/**
 * Created by yeol on 2019-04-24.
 */

public class Measure implements Parcelable {

    private String date;
    private String time;

    public Measure(String date, String time) {
        this.date = date;
        this.time = time;
    }

    protected Measure(Parcel in) {
        date = in.readString();
        time = in.readString();
    }

    public static final Creator<Measure> CREATOR = new Creator<Measure>() {
        @Override
        public Measure createFromParcel(Parcel in) {
            return new Measure(in);
        }

        @Override
        public Measure[] newArray(int size) {
            return new Measure[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(time);
    }


}