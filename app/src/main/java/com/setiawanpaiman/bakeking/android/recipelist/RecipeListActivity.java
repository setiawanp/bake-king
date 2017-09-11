package com.setiawanpaiman.bakeking.android.recipelist;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.setiawanpaiman.bakeking.android.BakeKingApplication;
import com.setiawanpaiman.bakeking.android.R;
import com.setiawanpaiman.bakeking.android.data.viewmodel.Recipe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListActivity extends AppCompatActivity
        implements RecipeListContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private RecipeListAdapter mAdapter;

    @Inject
    RecipeListPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);

        mAdapter = new RecipeListAdapter(this);
        recyclerView.setAdapter(mAdapter);

        DaggerRecipeListComponent.builder()
                .applicationComponent(((BakeKingApplication) getApplication()).getApplicationComponent())
                .recipeListModule(new RecipeListModule(this))
                .build()
                .inject(this);
        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void showRecipes(List<Recipe> recipes) {
        mAdapter.addData(recipes);
    }

    @Override
    public void showError() {
        Snackbar.make(recyclerView, R.string.error_recipe_list, Snackbar.LENGTH_LONG).show();
    }
}
