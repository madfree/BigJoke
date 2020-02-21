package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private final String STRING_JOKE = "joke";
    private String joke;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);

        MobileAds.initialize(getContext(), getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd = new InterstitialAd(this.getContext());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        final Button jokeButton = root.findViewById(R.id.button_tell_joke);
        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("Ads", "The institialAd wasn't loaded jet");
                }
            }
        });

        mInterstitialAd.setAdListener(new AdListener() {
            Intent jokeIntent = new Intent(getContext(), JokeActivity.class);
            @Override
            public void onAdLoaded() {
                Toast.makeText(getContext(), "onAdLoaded()", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(getContext(),
                        "onAdFailedToLoad() with error code: " + errorCode,
                        Toast.LENGTH_LONG).show();
            }
            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                joke = fetchJoke();
                Toast.makeText(getContext(),
                        "fetching joke",
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAdClosed() {
                if (joke == null || joke.equals("")) {
                    jokeIntent.putExtra(STRING_JOKE, "Sorry, no funny joke delivered :(");
                } else {
                    jokeIntent.putExtra(STRING_JOKE, joke);
                }
                getContext().startActivity(jokeIntent);
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        Log.d("Ads", "Loading ad in onResume");
    }

    private String fetchJoke() {
        try {
            String joke =  new EndpointsAsyncTask(getContext()).execute().get();
            return joke;
        } catch(InterruptedException e) {
            return e.getMessage();
        } catch (ExecutionException e) {
            return e.getMessage();
        }
    }

}
