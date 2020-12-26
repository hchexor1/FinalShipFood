package com.leekien.shipfoodfinal.login;

import android.util.Log;

import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.common.CommonActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Header;

public class LoginPresenter implements LoginManager.Presenter {
    private LoginManager.View view;
    private LoginManager.Interactor interactor;

    public LoginPresenter(LoginManager.View view) {
        this.view = view;
        interactor = new LoginInteractor();
    }

    @Override
    public void getInfo(final String username, final String password, String token, String auth) {
        Callback<User> callback = new Callback<User>() {
            @Override
            public void onResponse(Call<User> call4, Response<User> response) {
                if (!CommonActivity.isNullOrEmpty(response.body())) {
                    view.replace("0", response.body());
                }

            }

            @Override
            public void onFailure(Call<User> call5, Throwable t) {
            }
        };

        interactor.getInfo(callback, username, password, token, auth);

    }

    @Override
    public void login(String username, String password) {
        Callback callback = new Callback() {
            @Override
            public void onResponse(Call call2, Response response) {
                if (response.isSuccessful()) {
                    Headers headers = response.headers();
                    //token
                    List<String> list = Collections.singletonList(headers.get("Authorization"));
                    view.showInfoLogin("0", list.get(0));
                }
                else {
                    Log.d("TAG", "onResponse: "+response);
                    view.showInfoLogin("1", null);
                }


            }

            @Override
                public void onFailure(Call call3, Throwable t) {
                Log.e("TAG", "onFailure: ", t);
                view.showInfoLogin("1", null);
            }
        };
        // truyen callback de lay data ve
        interactor.login(callback, username, password);
    }


}
