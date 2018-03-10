package com.lasalle.mdpa.busybudgeter.manager;

import android.arch.lifecycle.LiveData;

import com.lasalle.mdpa.busybudgeter.database.entity.Budget;

import java.util.List;

public interface BudgetManager {

    LiveData<List<Budget>> getBudgetList();

    void insert(Budget budget);

    void fetchAll();

}
