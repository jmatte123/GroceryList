package com.joemattes.grocerylist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.joemattes.grocerylist.R;
import com.joemattes.grocerylist.data.Item;
import com.joemattes.grocerylist.data.ItemContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joematte on 9/3/17.
 */

/**
 * This class is for the recyclerView on the main activity.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    // --- Fields --- //
    private Context mContext;
    private Cursor mCursor;
    private ArrayList<Item> mData = null;
    private ArrayList<ItemViewHolder> viewHolders = new ArrayList<>();

    /**
     * Constructor for the ItemAdapter
     * @param context the current context of the ItemAdapter
     */
    public ItemAdapter(Context context){
        mContext = context;
    }

    /**
     * This method creates the view holder which holds the view which in turn holds the data
     * @param parent the viewGroup that this view will be apart of
     * @param viewType the type of view
     * @return the new ItemViewHolder
     */
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_layout, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        viewHolders.add(viewHolder);
        return viewHolder;
    }

    /**
     * bind the viewHolder with data
     * @param holder the view holder
     * @param position the position of the holder in the recyclerView
     */
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = mData.get(position);
        holder.mName.setText(item.getName().toString());
        holder.mArea.setText(getAreaCode(item.getArea().toString()));
        holder.mCheckBox.setChecked(false);
        Drawable areaColor = getColorFromItemArea(item.getArea().toString());
        holder.mImageView.setImageDrawable(areaColor);
    }

    /**
     * get the color from the colors to put the color in the area code circle
     * @param area the area
     * @return the color or null
     */
    private Drawable getColorFromItemArea(String area){
        switch (area) {
            case "Produce": return ContextCompat.getDrawable(mContext, R.drawable.produce_circle);
            case "Grocery": return ContextCompat.getDrawable(mContext, R.drawable.grocery_circle);
            case "Other": return ContextCompat.getDrawable(mContext, R.drawable.extra_circle);
            case "Meat": return ContextCompat.getDrawable(mContext, R.drawable.meat_circle);
            case "Frozen": return ContextCompat.getDrawable(mContext, R.drawable.frozen_circle);
            case "Dairy": return ContextCompat.getDrawable(mContext, R.drawable.dairy_circle);
            default: return null;
        }
    }

    /**
     * gets the area code from the area strings
     * @param area the area string
     * @return the area code
     */
    private String getAreaCode(String area){
        switch (area){
            case "Produce": return "P";
            case "Frozen" : return "F";
            case "Meat": return "M";
            case "Other": return "O";
            case "Grocery": return "G";
            case "Dairy": return "D";
            default:
                return "";
        }
    }

    /**
     * return the amount of data in the cursor
     * @return the amount of data
     */
    @Override
    public int getItemCount() {
        if (mCursor != null) return mCursor.getCount();
        else return 0;
    }

    /**
     * Swaps the Cursor with new data
     * @param newCursor the new data
     * @return the deleted cursor
     */
    public Cursor swapCursorWithNewData(Cursor newCursor){
        if (mCursor == newCursor) return null;
        Cursor temp = mCursor;
        mCursor = newCursor;

        if (newCursor != null) notifyDataSetChanged();
        return temp;
    }

    /**
     * Sort the cursor so that it is in the correct order
     */
    public void sortCursor(){
        mData = new ArrayList<>();
        for (int count = 0; count < getItemCount() + 6; count++) {
            while (mCursor.moveToNext()){
                String area = mCursor.getString(mCursor.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_AREA));
                if (count == 0 && area.equalsIgnoreCase("produce")){
                    String name = mCursor.getString(mCursor.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_NAME));
                    mData.add(new Item(name, area));
                } else if (count == 1 && area.equalsIgnoreCase("Meat")){
                    String name = mCursor.getString(mCursor.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_NAME));
                    mData.add(new Item(name, area));
                } else if (count == 2 && area.equalsIgnoreCase("Dairy")){
                    String name = mCursor.getString(mCursor.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_NAME));
                    mData.add(new Item(name, area));
                } else if (count == 3 && area.equalsIgnoreCase("Frozen")){
                    String name = mCursor.getString(mCursor.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_NAME));
                    mData.add(new Item(name, area));
                } else if (count == 4 && area.equalsIgnoreCase("Grocery")){
                    String name = mCursor.getString(mCursor.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_NAME));
                    mData.add(new Item(name, area));
                } else if (count == 5 && area.equalsIgnoreCase("Other")){
                    String name = mCursor.getString(mCursor.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_NAME));
                    mData.add(new Item(name, area));
                }
            }
            mCursor.moveToPosition(-1);
        }
        mCursor.moveToFirst();
    }

    /**
     * get the viewHolders in the recyclerView so that it can tell which holders are checked
     * @return viewHolders
     */
    public List<ItemViewHolder> getViewHolders() {
        return viewHolders;
    }

    /**
     * This class represents The items view holder which holds the elements of the data
     */
    class ItemViewHolder extends RecyclerView.ViewHolder{

        // --- Fields --- //
        final TextView mName;
        final TextView mArea;
        final ImageView mImageView;
        final CheckBox mCheckBox;

        /**
         * This is the constructor for the ItemViewHolder which sets up the viewHolder with relevant data
         * @param itemView this is the actual view that the data will be stored in
         */
        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCheckBox.isChecked()) mCheckBox.setChecked(false);
                    else mCheckBox.setChecked(true);
                }
            });
            mName = itemView.findViewById(R.id.tv_name);
            mArea = itemView.findViewById(R.id.tv_area);
            mImageView = itemView.findViewById(R.id.iv_area_color);
            mCheckBox = itemView.findViewById(R.id.checkBox);
        }

        /**
         * get the name to compare it to the database
         * @return mName
         */
        public String getmName() {
            return mName.getText().toString();
        }
    }
}
