package com.example.joemattes.grocerylist.data;

/**
 * Created by joematte on 9/7/17.
 */

/**
 * This class just represents an item that is displayed to the main RecyclerView
 */
public class Item {

    // --- Fields --- //
    private String name;
    private String area;
    private Boolean checked;

    /**
     * get the name of the item
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * get the area of the item
     * @return area
     */
    public String getArea(){
        return area;
    }

    /**
     * get the checked boolean of the item
     * @return checked
     */
    public Boolean getChecked() {return checked;}

    /**
     * creates a new item object
     * @param name the name of the item
     * @param area the area of the item
     */
    public Item(String name, String area, Boolean checked){
        this.name = name;
        this.area = area;
        this.checked = checked;
    }
}
