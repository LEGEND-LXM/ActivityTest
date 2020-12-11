package com.example.advancedskill;

import android.os.Parcel;
import android.os.Parcelable;

public class human implements Parcelable {
    private String name;
    private int age;


    protected human(Parcel in) {
        // 读取的顺序必须和写入的顺序相同
        name = in.readString();
        age = in.readInt();
    }

    // 泛型指定为 创建的类 的 类型
    public static final Creator<human> CREATOR = new Creator<human>() {
        @Override
        public human createFromParcel(Parcel in) {
            return new human(in);
        }

        // 创建空的 human 数组
        @Override
        public human[] newArray(int size) {
            return new human[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    // 将 human 中的字段一一写出
    // 字符串用 writeString()
    // 整形用 writeInt()
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }
}
