package com.udacity.gradle.builditbigger.backend;

import com.madfree.joketeller.JokeTeller;

/** The object model for the data we are sending through endpoints */
public class MyBean {

    private String myData;

    public String getData() {
        JokeTeller oneJoke = new JokeTeller();
        myData = oneJoke.getJoke();
        return myData;
    }

    public void setData(String data) {
        myData = data;
    }
}