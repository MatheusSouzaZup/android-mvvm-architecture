package br.com.zup.mvvmarchitecture.service;

import com.google.gson.Gson;

import br.com.zup.mvvmarchitecture.repository.BaseData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class BaseCallback<T extends Object> implements Callback<T> {

    public abstract void onSuccess(T response);

    public abstract void onError(BaseData error);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            try {
                onError(new Gson().fromJson(response.errorBody().string(), BaseData.class));
            } catch (Exception e) {
                e.printStackTrace();
                BaseData errorResponse = new BaseData();
                errorResponse.setMessage(e.getMessage());
                onError(errorResponse);
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        BaseData response = new BaseData();
        response.setMessage(t.getMessage());
        onError(response);
    }
}
