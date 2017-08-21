package com.setiawanpaiman.bakeking.android.data.source;

import android.support.annotation.NonNull;

import com.setiawanpaiman.bakeking.android.data.viewmodel.Recipe;
import com.setiawanpaiman.bakeking.android.http.api.RecipeApi;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Setiawan Paiman on 21/8/17.
 */

public class RecipeRepository implements RecipeDataSource {

    @NonNull
    private final RecipeApi mRecipeApi;

    @Inject
    public RecipeRepository(@NonNull RecipeApi recipeApi) {
        this.mRecipeApi = recipeApi;
    }

    @Override
    public Observable<List<Recipe>> getRecipes() {
        return mRecipeApi.getRecipes();
    }
}
