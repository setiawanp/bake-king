package com.setiawanpaiman.bakeking.android.data.source;

import com.setiawanpaiman.bakeking.android.data.viewmodel.Recipe;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Setiawan Paiman on 21/8/17.
 */

public interface RecipeDataSource {

    Observable<List<Recipe>> getRecipes();
}
