package com.setiawanpaiman.bakeking.android.recipelist;

import com.setiawanpaiman.bakeking.android.BasePresenter;
import com.setiawanpaiman.bakeking.android.BaseView;
import com.setiawanpaiman.bakeking.android.data.viewmodel.Recipe;

import java.util.List;

/**
 * Created by Setiawan Paiman on 22/8/17.
 */

public interface RecipeListContract {

    interface View extends BaseView {
        void showRecipes(List<Recipe> recipes);
        void showError();
    }

    interface Presenter extends BasePresenter {

    }
}
