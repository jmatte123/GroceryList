package com.example.joematte.grocerylist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.joematte.grocerylist.data.ItemContract;
import com.example.joematte.grocerylist.data.ItemDbHelper;

public class AddItemActivity extends AppCompatActivity {

    private Button mAdd;
    private RadioButton mFrozen, mGrocery, mDairy, mProduce, mMeat, mExtra;
    private RadioGroup mPDGrg, mFMErg;
    private EditText mName;

    private String mArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        findAllViews();
        MainActivity.GOING_BACK_TO_MAINACTIVITY = true;
    }

    private void findAllViews() {
        mAdd = (Button) findViewById(R.id.b_add);
        mFrozen = (RadioButton) findViewById(R.id.frozenRB);
        mGrocery = (RadioButton) findViewById(R.id.groceryRB);
        mDairy = (RadioButton) findViewById(R.id.dairyRB);
        mProduce = (RadioButton) findViewById(R.id.produceRB);
        mMeat = (RadioButton) findViewById(R.id.meatRB);
        mExtra = (RadioButton) findViewById(R.id.extraRB);
        mName = (EditText) findViewById(R.id.editText);
        mPDGrg = (RadioGroup) findViewById(R.id.pdgRG);
        mFMErg = (RadioGroup) findViewById(R.id.fmeRG);
    }

    public void addItemButtonClick(View view) {
        AddItemAsyncTask inBackground = new AddItemAsyncTask();
        inBackground.execute(mName.getText().toString(), mArea);
        finish();
    }

    public void anyButtonCkick(View view){
        switch (view.getId()){
            case R.id.produceB:
                if (!mProduce.isChecked()) {
                    mFMErg.clearCheck();
                    mProduce.setChecked(true);
                    mArea = ((Button) findViewById(R.id.produceB)).getText().toString();
                }
                break;
            case R.id.groceryB:
                if (!mGrocery.isChecked()) {
                    mFMErg.clearCheck();
                    mGrocery.setChecked(true);
                    mArea = ((Button) findViewById(R.id.groceryB)).getText().toString();
                }
                break;
            case R.id.frozenB:
                if (!mFrozen.isChecked()) {
                    mPDGrg.clearCheck();
                    mFrozen.setChecked(true);
                    mArea = ((Button) findViewById(R.id.frozenB)).getText().toString();
                }
                break;
            case R.id.meatB:
                if (!mMeat.isChecked()) {
                    mPDGrg.clearCheck();
                    mMeat.setChecked(true);
                    mArea = ((Button) findViewById(R.id.meatB)).getText().toString();
                }
                break;
            case R.id.extraB:
                if (!mExtra.isChecked()) {
                    mPDGrg.clearCheck();
                    mExtra.setChecked(true);
                    mArea = ((Button) findViewById(R.id.extraB)).getText().toString();
                }
                break;
            case R.id.dairyB:
                if (!mDairy.isChecked()) {
                    mFMErg.clearCheck();
                    mDairy.setChecked(true);
                    mArea = ((Button) findViewById(R.id.dairyB)).getText().toString();
                }
                break;
        }
    }

    class AddItemAsyncTask extends AsyncTask<String, Void, Long>{

        private SQLiteDatabase db;
        @Override
        protected Long doInBackground(String... strings) {
            String name = strings[0];
            String area = strings[1];

            ItemDbHelper dbHelper = new ItemDbHelper(AddItemActivity.this);
            db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(ItemContract.ItemEntry.COLUMN_NAME, name);
            values.put(ItemContract.ItemEntry.COLUMN_AREA, area);
            long key = 0;
            try {
                key = db.insert(ItemContract.ItemEntry.TABLE_NAME, null, values);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return key;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
