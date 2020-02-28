package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    private AdRequest adRequest;

    private final String STRING_JOKE = "joke";
    private String joke;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileAds.initialize(getActivity(),getString(R.string.admob_app_id));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);

        spinner = root.findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);

        hintTextView = root.findViewById(R.id.instructions_text_view);
        hintTextView.setVisibility(View.GONE);

        jokeButton = root.findViewById(R.id.button_tell_joke);
        jokeButton.setVisibility(View.GONE);

        adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.loadAd(adRequest);
        Log.d("Ads", "Loading ad in onCreateView");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Toast.makeText(getContext(), "onAdLoaded()", Toast.LENGTH_SHORT).show();
                spinner.setVisibility(View.GONE);
                hintTextView.setVisibility(View.VISIBLE);
                jokeButton.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when loading ad fails
                Toast.makeText(getContext(),
                        "onAdFailedToLoad() with error code: " + errorCode,
                        Toast.LENGTH_LONG).show();
                spinner.setVisibility(View.GONE);
                hintTextView.setVisibility(View.VISIBLE);
                jokeButton.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Toast.makeText(getContext(),
                        "Displaying Ad",
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAdClosed() {
                // Code to be executed when ad is closed
                displayJoke();
            }
        });

        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                hintTextView.setVisibility(View.GONE);
                jokeButton.setVisibility(View.GONE);

                joke = fetchJoke();
                Log.d("Ads", "Fetching joke in setOnCLickListener");
                showInterstitial();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        hintTextView.setVisibility(View.GONE);
        jokeButton.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            Log.d("Ads", "Starting spinner");
            mInterstitialAd.loadAd(adRequest);
            Log.d("Ads", "Loading ad in onResume");
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
            Intent jokeIntent = new Intent(getContext(), JokeActivity.class);
            if (joke == null || joke.equals("")) {
                jokeIntent.putExtra(STRING_JOKE, "Sorry, no funny joke delivered :(");
            } else {
                jokeIntent.putExtra(STRING_JOKE, joke);
            }
            getContext().startActivity(jokeIntent);
        }

        private void showInterstitial() {
            if (mInterstitialAd!= null && mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Toast.makeText(getContext(), "Something went wrong with the ad", Toast.LENGTH_LONG).show();
                displayJoke();
            }
        }
}


