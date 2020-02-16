package com.madfree.displayjoke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


public class JokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showJoke(getIntent().getStringExtra("jokeIntent"));
    }

    public void showJoke(String joke) {
        Toast.makeText(this, joke, Toast.LENGTH_SHORT).show();
    }

}
