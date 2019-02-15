package com.example.joemattes.grocerylist;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.joemattes.grocerylist.data.ItemContract;
import com.example.joemattes.grocerylist.data.ItemDbHelper;

/**
 * Created by joematte on 9/3/17.
 */

/**
 * this class represents the main activity or main entry point in the application
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    // --- Fields --- //
    FloatingActionButton fab;
    RecyclerView mRecyclerView;
    ItemAdapter mAdapter;
    public static boolean GOING_BACK_TO_MAINACTIVITY = false;
    private static final int ITEM_LOADER_ID = 640;

    /**
     * This creates the main activity
     * @param savedInstanceState if there is a savedInstanceState this will be used
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItemAdapter(this);
        mRecyclerView.setAdapter(mAdapter);


        fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_add_black);

        getSupportLoaderManager().initLoader(ITEM_LOADER_ID, null, this);
    }

    /**
     * This method will be called when the user goes away from the activity.
     *
     * This purpose of this method is to update the database for items that are checked in the recyclerView.
     */
    @Override
    protected void onPause() {
        super.onPause();
        ItemDbHelper dbHelper = new ItemDbHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (ItemAdapter.ItemViewHolder viewHolder : mAdapter.getViewHolders()) {
            if (viewHolder.mCheckBox.isChecked()) {
                String update = "UPDATE " + ItemContract.ItemEntry.TABLE_NAME + " SET "
                        + ItemContract.ItemEntry.COLUMN_CHECKED + " = \'true\' "
                        + "WHERE " + ItemContract.ItemEntry.COLUMN_NAME + " = "
                        + "\'" + viewHolder.getmName() + "\'";
                db.execSQL(update);
            }
        }
        getSupportLoaderManager().restartLoader(ITEM_LOADER_ID, null, MainActivity.this);
    }

    /**
     * create the menu with the delete button
     * @param menu the menu
     * @return true if it was made
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * if the delete button is selected this will get called
     * @param item the delete button because its the only one
     * @return true if the delete button was clicked
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            ItemDbHelper dbHelper = new ItemDbHelper(MainActivity.this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for (ItemAdapter.ItemViewHolder viewHolder : mAdapter.getViewHolders()) {
                if (viewHolder.mCheckBox.isChecked()) {
                    String selection = ItemContract.ItemEntry.COLUMN_NAME + " = " + "\'" + viewHolder.getmName() + "\'";
                    db.delete(ItemContract.ItemEntry.TABLE_NAME, selection, null);
                }
            }
            getSupportLoaderManager().restartLoader(ITEM_LOADER_ID, null, MainActivity.this);
            return true;
        } else
            return false;
    }

    /**
     * This method gets called when the user clicks the add Item button
     * @param view the button that was clicked
     */
    public void addItem(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }

    /**
     * This method resumes the activity if the user goes to another app and comes back
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (GOING_BACK_TO_MAINACTIVITY)
            getSupportLoaderManager().restartLoader(ITEM_LOADER_ID, null, this);

    }

    /**
     * This method creates a loader to load the data from the database to the recycler view
     * @param id the id of the loader
     * @param args the arguments of the loader
     * @return an asynchronous loader that returns the data
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTLoader(this);
    }

    /**
     * This method swaps the cursor with new data and sorts it
     * @param loader
     * @param data
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursorWithNewData(data);
        mAdapter.sortCursor();
    }

    /**
     * This resets the loader by swapping the cursor with nothing
     * @param loader the current loader
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursorWithNewData(null);
    }

    /**
     * This class represents the AsyncTaskLoader to query the data
     */
    public static class AsyncTLoader extends AsyncTaskLoader<Cursor> {

        // --- Fields --- //
        private Cursor mCursor = null;
        private SQLiteDatabase db;

        /**
         * Creates the AsyncTLoader
         * @param context the context of the app
         */
        public AsyncTLoader(Context context) {
            super(context);
        }

        /**
         * this method starts the loading process by force loading
         * the data if the data has not already been retrieved
         */
        @Override
        protected void onStartLoading() {
            if (mCursor != null) deliverResult(mCursor);
            else forceLoad();
        }

        /**
         * This method queries all the data from the database
         * @return the cursor that holds all the data
         */
        @Override
        public Cursor loadInBackground() {
            ItemDbHelper dbHelper = new ItemDbHelper(getContext());
            db = dbHelper.getReadableDatabase();
            try {
                return db.query(ItemContract.ItemEntry.TABLE_NAME, null, null, null, null, null, null);
            } catch (SQLiteException ex) {
                throw new UnknownError("Did not query");
            }
        }

        /**
         * this method delivers the result to the mCursor field
         * @param data the cursor that holds all the data from the database
         */
        @Override
        public void deliverResult(Cursor data) {
            mCursor = data;
            super.deliverResult(data);
        }

        /**
         * This method cancels the loader
         * @param data closes the cursor
         */
        @Override
        public void onCanceled(Cursor data) {
            data.close();
            db.close();
            super.onCanceled(data);
        }
    }
}
