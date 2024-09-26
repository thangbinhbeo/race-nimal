package com.example.prm392_miniproject_racenimal;

import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {
    String username;
    String password;
    double budget;

    public Account(String username, String password, double budget) {
        this.username = username;
        this.password = password;
        this.budget = budget;
    }

    protected Account(Parcel in) {
        username = in.readString();
        password = in.readString();
        budget = in.readDouble(); // Ensure all fields are read
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeDouble(budget); // Ensure all fields are written
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }
}

