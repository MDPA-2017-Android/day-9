package com.lasalle.mdpa.busybudgeter.network;

import android.arch.lifecycle.Observer;

import com.lasalle.mdpa.busybudgeter.database.entity.Budget;

import java.util.List;

public interface BudgetRestAPI {
    void fetchList(Observer<List<Budget>> budgetListObserver);
}
