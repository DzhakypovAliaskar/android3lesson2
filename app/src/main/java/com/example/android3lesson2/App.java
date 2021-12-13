package com.example.android3lesson2;

import android.app.Application;

import com.example.android3lesson2.data.remote.PostApi;
import com.example.android3lesson2.data.remote.RetrofitClient;

public class App extends Application{

    public static PostApi api;

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitClient client = new RetrofitClient();
        api = client.provideApi();
    }
}
