package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask <String, Void, String> {

    private static MyApi myApiService = null;
    private Context mContext;
    private final String STRING_JOKE = "joke";

    public EndpointsAsyncTask (Context context){
        mContext = context;
    }

    @Override
    protected String doInBackground(String... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }


        try {
            String joke = myApiService.tellJoke().execute().getData();
            Log.d("EndpointsAsyncTask", joke);
            return joke;
        } catch (IOException e) {
            return e.getMessage();
        }
    }


//    @Override
//    protected void onPostExecute(String result) {
//        Intent jokeIntent = new Intent(mContext, JokeActivity.class);
//        jokeIntent.putExtra(STRING_JOKE, result);
//        //startActivityForResult(jokeIntent, REQUEST_CODE_JOKE);
//        mContext.startActivity(jokeIntent);
//    }

}
