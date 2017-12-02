package com.example.joematte.grocerylist.data;

/**
 * Created by joematte on 9/7/17.
 */

public class Item {
    private String name;
    private String area;

    public String getName() {
        return name;
    }

    public String getArea(){
        return area;
    }



    public Item(String name, String area){
        this.name = name;
        this.area = area;
    }
}
