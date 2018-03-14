package com.lasalle.mdpa.busybudgeter.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.lasalle.mdpa.busybudgeter.database.entity.Budget;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public abstract class BudgetDao {

    @Query("Select * from budget order by id desc")
    public abstract LiveData<List<Budget>> getAll();

    @Insert(onConflict = REPLACE)
    public abstract void insert(Budget budget);

    @Insert(onConflict = REPLACE)
    public abstract void insertAll(Budget... budgetList);

    @Update
    public abstract void update(Budget budget);

    @Delete
    public abstract void delete(Budget budget);

    @Query("DELETE FROM budget")
    public abstract void deleteAll();

    @Transaction
    public void refreshDatabase(Budget... budgetList) {
        deleteAll();
        insertAll(budgetList);
    }

}
