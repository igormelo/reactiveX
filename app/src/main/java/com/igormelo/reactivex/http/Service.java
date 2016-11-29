package com.igormelo.reactivex.http;


import com.igormelo.reactivex.models.UserModel;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by root on 25/11/16.
 */

public interface Service {
    @GET("users/{login}")
    Observable<UserModel> search(@Path("login") String q);
}
