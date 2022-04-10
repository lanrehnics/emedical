package com.brandshoutcom.emedicals;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;
import com.wonderkiln.blurkit.BlurKit;

//import com.facebook.FacebookSdk;
//import com.facebook.appevents.AppEventsLogger;


/**
 * Created by LANReHNiCs on 5/26/2015.
 */
public class MyApplication extends Application {
    private static MyApplication sInstance;
    public static final String BASE_URL = "http://www.oacsofttech.com/";
    public static final String SITE_URL = BASE_URL + "medicals/";

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        BlurKit.init(this);

//        SocialLoginManager.init(this);
        // Initialize the SDK before executing any other operations,
        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);
    }


    public static MyApplication getInstance() {
        return sInstance;
    }


    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}
