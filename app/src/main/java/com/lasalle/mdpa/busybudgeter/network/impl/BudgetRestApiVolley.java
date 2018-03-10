package com.lasalle.mdpa.busybudgeter.network.impl;

import android.arch.lifecycle.Observer;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.lasalle.mdpa.busybudgeter.database.entity.Budget;
import com.lasalle.mdpa.busybudgeter.network.BudgetRestAPI;
import com.lasalle.mdpa.busybudgeter.network.util.GsonArrayRequest;

import java.util.List;

import javax.inject.Inject;

public class BudgetRestApiVolley implements BudgetRestAPI {
    //
    static final String FETCH_URL = "http://private-dc7936-budgeting1.apiary-mock.com/budgets";

    @Inject RequestQueue requestQueue;

    public void fetchList(Observer<List<Budget>> budgetListObserver)
    {
        GsonArrayRequest<Budget> gsonRequest = new GsonArrayRequest<>(
            FETCH_URL, Budget.class, null, response -> {
                budgetListObserver.onChanged(response);
            }, error -> {
                Log.d("Async", "Error!!");
            });
        requestQueue.add(gsonRequest);
    }
}
