package com.madfree.displayjoke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class JokeActivity extends AppCompatActivity {

    private final String STRING_JOKE = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        TextView jokeTv = findViewById(R.id.joke_text_view);

        if (getIntent() != null) {
            String jokeString = getIntent().getStringExtra(STRING_JOKE);
            jokeTv.setText(jokeString);
        } else {
            showNoJoke();
        }
    }

    public void showNoJoke() {
        Toast.makeText(this, "No funny line found!", Toast.LENGTH_SHORT).show();
    }

}
