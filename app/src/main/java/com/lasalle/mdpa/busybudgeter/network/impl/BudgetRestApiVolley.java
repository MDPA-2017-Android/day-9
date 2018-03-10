package com.lasalle.mdpa.busybudgeter.network.impl;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.lasalle.mdpa.busybudgeter.database.entity.Budget;
import com.lasalle.mdpa.busybudgeter.network.BudgetRestAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class BudgetRestApiVolley implements BudgetRestAPI {
    //
    static final String FETCH_URL = "http://private-dc7936-budgeting1.apiary-mock.com/budgets";

    @Inject RequestQueue requestQueue;

    public void fetchList(Observer<List<Budget>> budgetListObserver)
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, FETCH_URL, null, successResponse -> {
                    List<Budget> parsedList = parseBudgetList(successResponse);
                    budgetListObserver.onChanged(parsedList);
                }, errorResponse -> {
                    // TODO: Handle error
                    Log.d("Async", "Error!!");
                });

        requestQueue.add(jsonArrayRequest);
    }

    private List<Budget> parseBudgetList(JSONArray jsonArray)
    {
        List<Budget> budgetList = new ArrayList<>();

        try {
            for(int i = 0; i < jsonArray.length(); ++i)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Budget budget = new Budget();
                budget.setId(jsonObject.getInt("id"));
                budget.setName(jsonObject.getString("name"));

                budgetList.add(budget);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return budgetList;
    }
}
