package com.lasalle.mdpa.busybudgeter.network.impl;

import android.arch.lifecycle.Observer;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lasalle.mdpa.busybudgeter.database.entity.Budget;
import com.lasalle.mdpa.busybudgeter.network.BudgetRestAPI;
import com.lasalle.mdpa.busybudgeter.network.util.GsonArrayRequest;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BudgetRestApiOkHttp implements BudgetRestAPI {
    //
    static final String FETCH_URL = "http://private-dc7936-budgeting1.apiary-mock.com/budgets";

    @Inject OkHttpClient okHttpClient;
    @Inject Gson gson;

    public void fetchList(Observer<List<Budget>> budgetListObserver)
    {
        Request request = new Request.Builder()
                .url(FETCH_URL)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            // Parse response using gson deserializer
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // Process the data on the worker thread
                List<Budget> budgetList = gson.fromJson(
                        response.body().charStream(),
                        new TypeToken<List<Budget>>(){}.getType());

                budgetListObserver.onChanged(budgetList);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Async", "Error!!");
            }
        });
    }
}
