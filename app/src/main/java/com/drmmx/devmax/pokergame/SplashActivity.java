package com.drmmx.devmax.pokergame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.drmmx.devmax.pokergame.game.HomeScreenActivity;
import com.drmmx.devmax.pokergame.model.TextData;
import com.drmmx.devmax.pokergame.retrofit.TextAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity_";

    public static final String ID_URL = "1UcrqL2YVQRyrgHzEqCfqKbC0ktbpoG0d4_xVZY8pimQ";
    public static final String WEB_VIEW_LINK = "web_view_link";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getData();
    }

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TextAPI textAPI = retrofit.create(TextAPI.class);

        Call<TextData> call = textAPI.getData(SplashActivity.ID_URL);
        call.enqueue(new Callback<TextData>() {
            @Override
            public void onResponse(@NonNull Call<TextData> call, @NonNull Response<TextData> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String openParam = response.body().getTextData().get(0).getOpen();
                    String openLinkParam = response.body().getTextData().get(0).getOpenLink();
                    if (openParam.equals("link")) {
                        Intent intent = new Intent(SplashActivity.this, WebViewActivity.class);
                        intent.putExtra(WEB_VIEW_LINK, openLinkParam);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SplashActivity.this, HomeScreenActivity.class);
                        startActivity(intent);
                    }
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TextData> call, @NonNull Throwable t) {
                Log.d(TAG, t.getMessage());
                Intent intent = new Intent(SplashActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });
    }
}
