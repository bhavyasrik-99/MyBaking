package com.example.mybaking;

import android.os.Parcel;
import android.os.Parcelable;

public class BakePojo implements Parcelable {
    String rname;
    String ring;
    String steps;

    protected BakePojo(Parcel in) {
        rname=in.readString();ring=in.readString();steps=in.readString();

    }

    public static final Creator<BakePojo> CREATOR = new Creator<BakePojo>() {
        @Override
        public BakePojo createFromParcel(Parcel in) {
            return new BakePojo(in);
        }

        @Override
        public BakePojo[] newArray(int size) {
            return new BakePojo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rname);
        dest.writeString(ring);
        dest.writeString(steps);
    }

    public BakePojo(String rname, String ring, String steps) {
        this.rname = rname;
        this.ring = ring;
        this.steps = steps;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRing() {
        return ring;
    }

    public void setRing(String ring) {
        this.ring = ring;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
}
