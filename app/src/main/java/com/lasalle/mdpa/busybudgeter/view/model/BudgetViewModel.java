package com.lasalle.mdpa.busybudgeter.view.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.lasalle.mdpa.busybudgeter.database.entity.Budget;
import com.lasalle.mdpa.busybudgeter.manager.BudgetManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import toothpick.Scope;
import toothpick.Toothpick;
import toothpick.config.Module;

public class BudgetViewModel extends AndroidViewModel {

    @Inject BudgetManager budgetManager;
    private MutableLiveData<List<String>> nameList;

    public BudgetViewModel(@NonNull Application application) {
        super(application);
        Scope scope = Toothpick.openScopes(getApplication(), this);
        scope.installModules(new Module() {{
            bind(UserLoginViewModel.class);
        }});
        Toothpick.inject(this, scope);

        nameList = new MutableLiveData<>();
        budgetManager.fetchAll();
    }

    public LiveData<List<String>> getBudgetNameList() {
        return Transformations.map(
                budgetManager.getBudgetList(),
                budgetList -> {
                    List<String> budgetNameList = new ArrayList<>();

                    for(Budget budget : budgetList)
                    {
                        budgetNameList.add(budget.getName());
                    }

                    return budgetNameList;
                });
    }

    public void insertNewBudget(String budgetName) {
        Budget budget = new Budget();
        budget.setName(budgetName);

        budgetManager.insert(budget);
    }

    @Override
    protected void onCleared() {
        Toothpick.closeScope(this);
        super.onCleared();
    }
}
