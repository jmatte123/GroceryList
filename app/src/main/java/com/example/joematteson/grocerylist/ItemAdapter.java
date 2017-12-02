package com.example.joematte.grocerylist;

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

import com.example.joematte.grocerylist.data.Item;
import com.example.joematte.grocerylist.data.ItemContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joematte on 9/3/17.
 */

/**
 * This class is for the recyclerView on the main activity.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    private ArrayList<Item> mData = null;
    private List<RecyclerView.ViewHolder> checkedHolders;

    public ItemAdapter(Context context){
        mContext = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        int idIndex = mCursor.getColumnIndex(ItemContract.ItemEntry._ID);
        mCursor.moveToPosition(position);
        final int id = mCursor.getInt(idIndex);
        holder.itemView.setTag(id);
        Item item = mData.get(position);
        holder.mName.setText(item.getName().toString());
        holder.mArea.setText(getAreaCode(item.getArea().toString()));
        holder.mCheckBox.setChecked(false);
        Drawable areaColor = getColorFromItemArea(item.getArea().toString());
        holder.mImageView.setImageDrawable(areaColor);
    }

    private Drawable getColorFromItemArea(String area){
        switch (area) {
            case "Produce": return ContextCompat.getDrawable(mContext, R.drawable.produce_circle);
            case "Grocery": return ContextCompat.getDrawable(mContext, R.drawable.grocery_circle);
            case "Extra": return ContextCompat.getDrawable(mContext, R.drawable.extra_circle);
            case "Meat": return ContextCompat.getDrawable(mContext, R.drawable.meat_circle);
            case "Frozen": return ContextCompat.getDrawable(mContext, R.drawable.frozen_circle);
            case "Dairy": return ContextCompat.getDrawable(mContext, R.drawable.dairy_circle);
            default: return null;
        }
    }

    private String getAreaCode(String area){
        switch (area){
            case "Produce": return "P";
            case "Frozen" : return "F";
            case "Meat": return "M";
            case "Extra": return "E";
            case "Grocery": return "G";
            case "Dairy": return "D";
            default:
                return "";
        }
    }

    @Override
    public int getItemCount() {
        if (mCursor != null) return mCursor.getCount();
        else return 0;
    }


    public Cursor swapCursorWithNewData(Cursor newCursor){
        if (mCursor == newCursor) return null;
        Cursor temp = mCursor;
        mCursor = newCursor;

        if (newCursor != null) notifyDataSetChanged();
        return temp;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        final TextView mName;
        final TextView mArea;
        final ImageView mImageView;
        final CheckBox mCheckBox;

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
    }

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
                } else if (count == 5 && area.equalsIgnoreCase("Extra")){
                    String name = mCursor.getString(mCursor.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_NAME));
                    mData.add(new Item(name, area));
                }
            }
            mCursor.moveToPosition(-1);
        }
        mCursor.moveToFirst();
    }

    public ArrayList<Item> getmData() {
        return mData;
    }
}
