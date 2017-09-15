package com.setiawanpaiman.bakeking.android.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.setiawanpaiman.bakeking.android.BakeKingApplication;
import com.setiawanpaiman.bakeking.android.R;
import com.setiawanpaiman.bakeking.android.data.viewmodel.Recipe;
import com.setiawanpaiman.bakeking.android.recipelist.DaggerRecipeListComponent;
import com.setiawanpaiman.bakeking.android.recipelist.RecipeListContract;
import com.setiawanpaiman.bakeking.android.recipelist.RecipeListModule;
import com.setiawanpaiman.bakeking.android.recipelist.RecipeListPresenter;
import com.setiawanpaiman.bakeking.android.util.TextFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * The configuration screen for the {@link RecipeWidget RecipeWidget} AppWidget.
 */
public class RecipeWidgetConfigureActivity extends AppCompatActivity
        implements RecipeListContract.View {

    private static final String PREFS_NAME = RecipeWidget.class.getName();
    private static final String PREF_RECIPE_NAME_PREFIX_KEY = "recipename_";
    private static final String PREF_INGREDIENTS_PREFIX_KEY = "recipeingredients_";

    @Inject
    RecipeListPresenter mPresenter;

    private List<Recipe> mRecipes = new ArrayList<>();
    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private Spinner spinner;

    View.OnClickListener mOnClickListener = v -> {
        final Context context = RecipeWidgetConfigureActivity.this;

        // When the button is clicked, store the string locally
        final Recipe selectedRecipe = mRecipes.get(spinner.getSelectedItemPosition());
        saveRecipePref(context, mAppWidgetId, selectedRecipe.getName(),
                TextFactory.constructIngredientsText(RecipeWidgetConfigureActivity.this,
                        selectedRecipe.getIngredients()));

        // It is the responsibility of the configuration activity to update the app widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RecipeWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    };

    // Write the prefix to the SharedPreferences object for this widget
    static void saveRecipePref(final Context context, final int appWidgetId,
                               final String recipeName, final String ingredients) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_RECIPE_NAME_PREFIX_KEY + appWidgetId, recipeName);
        prefs.putString(PREF_INGREDIENTS_PREFIX_KEY + appWidgetId, ingredients);
        prefs.apply();
    }

    static String loadRecipeNamePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(PREF_RECIPE_NAME_PREFIX_KEY + appWidgetId, null);
    }

    static String loadIngredientsPref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(PREF_INGREDIENTS_PREFIX_KEY + appWidgetId, null);
    }

    static void deleteRecipePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_RECIPE_NAME_PREFIX_KEY + appWidgetId);
        prefs.remove(PREF_INGREDIENTS_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.recipe_widget_configure);
        spinner = (Spinner) findViewById(R.id.spinner);
        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);
        DaggerRecipeListComponent.builder()
                .applicationComponent(((BakeKingApplication) getApplication()).getApplicationComponent())
                .recipeListModule(new RecipeListModule(this))
                .build()
                .inject(this);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        mPresenter.subscribe();
    }

    @Override
    public void showRecipes(List<Recipe> recipes) {
        mRecipes.clear();
        mRecipes.addAll(recipes);

        List<String> recipesName = new ArrayList<>();
        for (Recipe recipe : mRecipes) {
            recipesName.add(recipe.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, recipesName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.error_recipe_list, Toast.LENGTH_LONG).show();
        finish();
    }
}

