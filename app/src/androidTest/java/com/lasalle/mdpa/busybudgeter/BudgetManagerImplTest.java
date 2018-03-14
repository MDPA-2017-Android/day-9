package com.lasalle.mdpa.busybudgeter;

import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import com.lasalle.mdpa.busybudgeter.database.BudgetingDatabase;
import com.lasalle.mdpa.busybudgeter.database.entity.Budget;
import com.lasalle.mdpa.busybudgeter.manager.BudgetManager;
import com.lasalle.mdpa.busybudgeter.manager.impl.BudgetManagerImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.inject.Inject;

import toothpick.Scope;
import toothpick.Toothpick;
import toothpick.config.Module;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class BudgetManagerImplTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private BudgetManager budgetManager;

    private BudgetingDatabase budgetingDatabase;

    @Mock
    private Observer<List<Budget>> budgetObserver;

    @Before
    public void setup() {
        Context context = InstrumentationRegistry.getTargetContext();
        BudgetingApplication application = (BudgetingApplication) context.getApplicationContext();
        Scope scope = Toothpick.openScopes(application, this);

        // Create new database so we make sure that everything is empty
        budgetingDatabase = Room.inMemoryDatabaseBuilder(context, BudgetingDatabase.class).build();

        scope.installTestModules(new Module() {{
            bind(BudgetingDatabase.class).toInstance(budgetingDatabase);
        }});

        budgetManager = new BudgetManagerImpl();
        Toothpick.inject(budgetManager, scope);
    }

    @After
    public void tearDown() {
        Toothpick.closeScope(this);
    }

    @Test
    public void fetchItemFromNetworkRequest() throws InterruptedException {
        // Setup observer
        budgetManager.getBudgetList().observeForever(budgetObserver);

        budgetManager.fetchAll();

        // Verify calls.
        ArgumentCaptor<List<Budget>> budgetListArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(budgetObserver, timeout(10000).times(2)).onChanged(budgetListArgumentCaptor.capture());

        assertEquals(2, budgetListArgumentCaptor.getValue().size());
        assertEquals("HTPC", budgetListArgumentCaptor.getValue().get(1).getName());
    }
}
