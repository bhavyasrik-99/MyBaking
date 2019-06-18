package com.example.mybaking;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeStepsPojo implements Parcelable {
    String sid;
    String ssd;
    String sd;
    String videopath;
    String turl;

    protected RecipeStepsPojo(Parcel in) {
        sid=in.readString();
        ssd=in.readString();
        sd=in.readString();
        videopath=in.readString();
        turl=in.readString();

    }

    public static final Creator<RecipeStepsPojo> CREATOR = new Creator<RecipeStepsPojo>() {
        @Override
        public RecipeStepsPojo createFromParcel(Parcel in) {
            return new RecipeStepsPojo(in);

        }

        @Override
        public RecipeStepsPojo[] newArray(int size) {
            return new RecipeStepsPojo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sid);
        dest.writeString(ssd);
        dest.writeString(sd);dest.writeString(videopath);
        dest.writeString(turl);
    }

    public RecipeStepsPojo(String sid, String ssd, String sd, String videopath, String turl) {
        this.sid = sid;
        this.ssd = ssd;
        this.sd = sd;
        this.videopath = videopath;
        this.turl = turl;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSsd() {
        return ssd;
    }

    public void setSsd(String ssd) {
        this.ssd = ssd;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public String getVideopath() {
        return videopath;
    }

    public void setVideopath(String videopath) {
        this.videopath = videopath;
    }

    public String getTurl() {
        return turl;
    }

    public void setTurl(String turl) {
        this.turl = turl;
    }
}
