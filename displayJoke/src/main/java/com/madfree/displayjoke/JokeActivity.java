package com.madfree.displayjoke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.madfree.joketeller.JokeTeller;

public class JokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchJoke();
    }

    public String fetchJoke() {
        JokeTeller joke = new JokeTeller();
        return joke.getJoke();
    }
}
