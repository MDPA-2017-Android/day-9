package com.lasalle.mdpa.trinder.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.lasalle.mdpa.trinder.database.converters.DateConverters;
import com.lasalle.mdpa.trinder.model.User;

/*
public abstract class BudgetingDatabase extends RoomDatabase {

    public abstract BudgetDao getBudgetDao();

}*/

@Database(entities = {User.class}, version = 1)
@TypeConverters({DateConverters.class})
public abstract class TrinderDatabase extends RoomDatabase {

}
