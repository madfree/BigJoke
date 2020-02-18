package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class EndpointsAsyncTaskTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testAsyncTask() throws InterruptedException, ExecutionException {
        AsyncTask asyncTask = new EndpointsAsyncTask(activityTestRule.getActivity()).execute();

        String joke = asyncTask.get().toString();

        assertNotNull(joke);
    }
}