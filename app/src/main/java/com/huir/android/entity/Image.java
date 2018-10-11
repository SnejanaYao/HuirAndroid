package com.huir.android.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 获取视频图片的实体类
 */
public class Image implements Parcelable {
    private String path;
    private long time;
    private String name;
    private String mineType;

    public Image(String path,long time,String name,String mineType) {
        this.path = path;
        this.time = time;
        this.name = name;
        this.mineType = mineType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMineType() {
        return mineType;
    }

    public void setMineType(String mineType) {
        this.mineType = mineType;
    }

    public boolean isGif(){
        return "image/gif".equals(mineType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeLong(this.time);
        dest.writeString(this.name);
        dest.writeString(this.mineType);
    }

    protected  Image(Parcel parcel){
        this.path  = parcel.readString();
        this.time = parcel.readLong();
        this.name = parcel.readString();
        this.mineType = parcel.readString();
    }


    public static  final  Parcelable.Creator<Image> CREATOR= new Parcelable.Creator<Image>(){
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    @Override
    public boolean equals(Object obj) {
        Image image = (Image) obj;
        return path.equals(image.getPath())  && name.equals(image.getName())  && time == image.getTime();
    }
}
