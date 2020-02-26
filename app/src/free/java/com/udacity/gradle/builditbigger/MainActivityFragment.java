package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.madfree.displayjoke.JokeActivity;

import java.util.concurrent.ExecutionException;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private InterstitialAd mInterstitialAd;
    private ProgressBar spinner;
    private TextView hintTextView;
    private Button jokeButton;

    private final String STRING_JOKE = "joke";
    private String joke;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);

        spinner = root.findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        hintTextView = root.findViewById(R.id.instructions_text_view);
        hintTextView.setVisibility(View.GONE);
        jokeButton = root.findViewById(R.id.button_tell_joke);
        jokeButton.setVisibility(View.GONE);

//        MobileAds.initialize(getActivity(), getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Toast.makeText(getActivity(), "onAdLoaded()", Toast.LENGTH_SHORT).show();
                spinner.setVisibility(View.GONE);
                hintTextView.setVisibility(View.VISIBLE);
                jokeButton.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(getActivity(),
                        "onAdFailedToLoad() with error code: " + errorCode,
                        Toast.LENGTH_LONG).show();
                spinner.setVisibility(View.GONE);
                hintTextView.setVisibility(View.VISIBLE);
                jokeButton.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Toast.makeText(getActivity(),
                        "Displaying Ad",
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAdClosed() {
                displayJoke();
            }
        });

        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                Log.d("Ads", "Starting spinner");
                if (spinner.isShown()) {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("Ads", "The institialAd wasn't loaded jet");
                        displayJoke();
                    }
                }
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        spinner.setVisibility(View.VISIBLE);
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            Log.d("Ads", "Starting spinner");
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
            Log.d("Ads", "Loading ad in onResume");
        }
        if (joke == null || joke.isEmpty()) {
            joke = fetchJoke();
            Log.d("Ads", "Fetching joke");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        spinner.setVisibility(View.GONE);
    }

    private String fetchJoke() {
        try {
            joke =  new EndpointsAsyncTask(getContext()).execute().get();
            return joke;
        } catch(InterruptedException e) {
            return e.getMessage();
        } catch (ExecutionException e) {
            return e.getMessage();
        }
    }

    private void displayJoke() {
            Intent jokeIntent = new Intent(getActivity(), JokeActivity.class);
            if (joke == null || joke.equals("")) {
                jokeIntent.putExtra(STRING_JOKE, "Sorry, no funny joke delivered :(");
            } else {
                jokeIntent.putExtra(STRING_JOKE, joke);
            }
            getActivity().startActivity(jokeIntent);
        }
}
