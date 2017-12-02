package com.example.joematte.grocerylist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.system.ErrnoException;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.joematte.grocerylist.data.ItemContract;
import com.example.joematte.grocerylist.data.ItemDbHelper;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    FloatingActionButton fab;
    RecyclerView mRecyclerView;
    ItemAdapter mAdapter;

    public static boolean GOING_BACK_TO_MAINACTIVITY = false;

    private static final int ITEM_LOADER_ID = 640;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItemAdapter(this);
        mRecyclerView.setAdapter(mAdapter);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_add_black);

        getSupportLoaderManager().initLoader(ITEM_LOADER_ID, null, this);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }


            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int id = (int) viewHolder.itemView.getTag();

                ItemDbHelper dbHelper = new ItemDbHelper(MainActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                String selection = ItemContract.ItemEntry._ID + " = " + String.valueOf(id);
                db.delete(ItemContract.ItemEntry.TABLE_NAME, selection, null);
                getSupportLoaderManager().restartLoader(ITEM_LOADER_ID, null, MainActivity.this);
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    public void addItem(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GOING_BACK_TO_MAINACTIVITY)
            getSupportLoaderManager().restartLoader(ITEM_LOADER_ID, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            private Cursor mCursor = null;
            private SQLiteDatabase db;

            @Override
            protected void onStartLoading() {
                if (mCursor != null) deliverResult(mCursor);
                else forceLoad();
            }

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

            @Override
            public void deliverResult(Cursor data) {
                mCursor = data;
                super.deliverResult(data);
            }

            @Override
            public void onCanceled(Cursor data) {
                data.close();
                db.close();
                super.onCanceled(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursorWithNewData(data);
        mAdapter.sortCursor();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursorWithNewData(null);
    }
}
