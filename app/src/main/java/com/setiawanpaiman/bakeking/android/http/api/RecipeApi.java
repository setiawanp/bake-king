package com.setiawanpaiman.bakeking.android.http.api;

import com.setiawanpaiman.bakeking.android.data.viewmodel.Recipe;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Setiawan Paiman on 21/8/17.
 */

public interface RecipeApi {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Observable<List<Recipe>> getRecipes();
}
