package com.drmmx.devmax.pokergame.retrofit;

import com.drmmx.devmax.pokergame.model.TextData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TextAPI {

    @GET("exec")
    Call<TextData> getData(@Query("id") String postId);
}
