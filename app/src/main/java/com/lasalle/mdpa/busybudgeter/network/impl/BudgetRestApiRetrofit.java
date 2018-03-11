package com.lasalle.mdpa.busybudgeter.network.impl;

import android.arch.lifecycle.Observer;
import android.util.Log;

import com.lasalle.mdpa.busybudgeter.database.entity.Budget;
import com.lasalle.mdpa.busybudgeter.network.BudgetRestAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class BudgetRestApiRetrofit implements BudgetRestAPI {
    //
    static final String FETCH_URL = "http://private-dc7936-budgeting1.apiary-mock.com";

    protected interface BudgetAPI {
        @GET("budgets")
        Call<List<Budget>> listBudgets();
    }

    private Retrofit retrofit;
    private BudgetAPI budgetAPI;

    public BudgetRestApiRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(FETCH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        budgetAPI = retrofit.create(BudgetAPI.class);
    }

    @Override
    public void fetchList(Observer<List<Budget>> budgetListObserver) {
        Call<List<Budget>> budgets = budgetAPI.listBudgets();
        budgets.enqueue(new Callback<List<Budget>>() {
            @Override
            public void onResponse(Call<List<Budget>> call, Response<List<Budget>> response) {
                budgetListObserver.onChanged(response.body());
            }

            @Override
            public void onFailure(Call<List<Budget>> call, Throwable t) {
                Log.d("Async", "Error!!");
            }
        });
    }


}
