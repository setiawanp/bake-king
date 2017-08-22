package com.setiawanpaiman.bakeking.android.util.scheduler;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

/**
 * Created by Setiawan Paiman on 22/8/17.
 */

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
