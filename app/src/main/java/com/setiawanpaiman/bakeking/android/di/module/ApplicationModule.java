package com.setiawanpaiman.bakeking.android.di.module;

import com.setiawanpaiman.bakeking.android.http.api.ApiFactory;
import com.setiawanpaiman.bakeking.android.http.api.RecipeApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Setiawan Paiman on 21/8/17.
 */

@Module
public class ApplicationModule {

    @Provides
    @Singleton
    ApiFactory providesApiFactory() {
        return new ApiFactory();
    }

    @Provides
    @Singleton
    RecipeApi providesRecipeApi(final ApiFactory apiFactory) {
        return apiFactory.create(RecipeApi.class);
    }
}
