package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.madfree.displayjoke.JokeActivity;

import java.util.concurrent.ExecutionException;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ProgressBar spinner;
    private TextView hintTextView;
    private Button jokeButton;

    private String joke;
    private final String STRING_JOKE = "joke";

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        spinner = root.findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        hintTextView = root.findViewById(R.id.instructions_text_view);
        jokeButton = root.findViewById(R.id.button_tell_joke);

        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                joke = fetchJoke();
                spinner.setVisibility(View.GONE);
                displayJoke();
            }
        });
        return root;
    }


    private String fetchJoke() {
        try {
            joke =  new EndpointsAsyncTask(getContext()).execute().get();
            return joke;
        } catch(InterruptedException | ExecutionException e) {
            return e.getMessage();
        }
    }

    private void displayJoke() {
        Intent jokeIntent = new Intent(getContext(), JokeActivity.class);
        if (joke == null || joke.equals("")) {
            jokeIntent.putExtra(STRING_JOKE, "Sorry, no funny joke delivered :(");
        } else {
            jokeIntent.putExtra(STRING_JOKE, joke);
        }
        getContext().startActivity(jokeIntent);
    }

}
