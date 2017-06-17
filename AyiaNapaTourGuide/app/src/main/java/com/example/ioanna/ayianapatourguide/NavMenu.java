package com.example.ioanna.ayianapatourguide;

/**
 * Created by Ioanna on 17/06/2017.
 */

public class NavMenu {
    //Navigation menu
    enum MenuItems{
        About,
        Beaches,
        Restaurants,
        Nightlife,
        ThingsToDo
    }

    //Types of list view items
    enum ItemType{
        Simple, //image and title
        Description,  //image, title and short description
        Restaurant
    }
}
