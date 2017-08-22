package com.setiawanpaiman.bakeking.android.recipelist;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Setiawan Paiman on 22/8/17.
 */

@Module
public class RecipeListModule {

    private final RecipeListContract.View mView;

    public RecipeListModule(RecipeListContract.View view) {
        this.mView = view;
    }

    @Provides
    RecipeListContract.View providesView() {
        return mView;
    }
}
