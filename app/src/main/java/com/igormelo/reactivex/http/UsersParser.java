package com.igormelo.reactivex.http;

import com.igormelo.reactivex.models.UserModel;

import java.io.IOException;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by root on 25/11/16.
 */

public class UsersParser {
    public static Observable<UserModel> searchByLogin(String q) throws IOException {
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .build();
        Service api = retrofit.create(Service.class);
        return api.search(q);
    }
}
