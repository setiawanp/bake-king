package com.setiawanpaiman.bakeking.android;

import android.app.Application;
import android.support.annotation.NonNull;

import com.setiawanpaiman.bakeking.android.di.component.ApplicationComponent;
import com.setiawanpaiman.bakeking.android.di.component.DaggerApplicationComponent;

/**
 * Created by Setiawan Paiman on 21/8/17.
 */

public class BakeKingApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.create();
    }

    @NonNull
    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
