package com.example.b3s.account;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Token implements Parcelable {
    private String refresh;
    private String access;

    public Token() {
    }

    public Token(String refresh, String access) {
        this.refresh = refresh;
        this.access = access;
    }

    protected Token(Parcel in) {
        refresh = in.readString();
        access = in.readString();
    }

    public static final Creator<Token> CREATOR = new Creator<Token>() {
        @Override
        public Token createFromParcel(Parcel in) {
            return new Token(in);
        }

        @Override
        public Token[] newArray(int size) {
            return new Token[size];
        }
    };

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(refresh);
        parcel.writeString(access);
    }
}
