package com.joemattes.grocerylist;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.joemattes.grocerylist.R;
import com.joemattes.grocerylist.data.ItemContract;
import com.joemattes.grocerylist.data.ItemDbHelper;

/**
 * Created by joematte on 9/3/17.
 */

/**
 * This class is the activity for adding items to the mainActivities RecyclerView
 */
public class AddItemActivity extends AppCompatActivity {

    // --- Fields --- //
    private Button mAdd;
    private RadioButton mFrozen, mGrocery, mDairy, mProduce, mMeat, mExtra;
    private RadioGroup mPDGrg, mFMErg;
    private EditText mName;
    private String mArea;

    /**
     * Creates the activity
     * @param savedInstanceState if the user goes back to this activity there will be
     *                           a saved state that the activity go use
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        findAllViews();
        MainActivity.GOING_BACK_TO_MAINACTIVITY = true;
    }

    /**
     * This class is used to deal with user action in the top menu
     * @param item the item that was selected by the user
     * @return if it worked
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * this finds all the views for the activity so it can create the activity
     */
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

    /**
     * This item is called when the add button is clicked, calls the asynchronous class
     * @param view the view that was clicked
     */
    public void addItemButtonClick(View view) {
        AddItemAsyncTask inBackground = new AddItemAsyncTask();
        inBackground.execute(mName.getText().toString(), mArea);
        finish();
    }

    /**
     * This method is in charge of changing the radio buttons
     * @param view which ever radio button was clicked
     */
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

    /**
     * This class asynchronously adds a data point to the database
     */
    class AddItemAsyncTask extends AsyncTask<String, Void, Long>{

        // --- Fields --- //
        private SQLiteDatabase db;

        /**
         * This method does the asynchronous putting the data point into the data base in the background
         * @param strings these are the data points that are put into the database
         * @return the position that the data point is in the database
         */
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

        /**
         * this is called when the background task is done
         * @param aLong the data point
         */
        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }
    }
}
