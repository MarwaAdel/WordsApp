package com.example.wordsapp;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;

import com.instabug.featuresrequest.FeatureRequests;
import com.instabug.library.Instabug;
import com.instabug.library.invocation.InstabugInvocationEvent;
import com.instabug.library.util.LocaleHelper;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;

/**
 * Created by Marwa Adel on 10/1/2021.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new Instabug.Builder(this, "3e097cb41ba8edc126aa3dd5f720de3f")
                .setInvocationEvents(InstabugInvocationEvent.SHAKE, InstabugInvocationEvent.SCREENSHOT)
                .build();
    }


}

